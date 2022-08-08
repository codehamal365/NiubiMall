package com.gl.lisen;

import com.alibaba.fastjson.JSON;
import com.gl.entity.Course;
import com.gl.service.ICourseService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CourseMQListener {

    public static final String QUEUE_COURSE_SAVE = "queue.course.save";
    public static final String QUEUE_COURSE_REMOVE = "queue.course.remove";
    public static final String KEY_COURSE_SAVE = "key.course.save";
    public static final String KEY_COURSE_REMOVE = "key.course.remove";
    public static final String COURSE_EXCHANGE = "course.exchange";
    
    @Autowired
    ICourseService courseService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 监听课程添加操作
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = QUEUE_COURSE_SAVE, durable = "true"),
                    exchange = @Exchange(value = COURSE_EXCHANGE,
                            type = ExchangeTypes.TOPIC,
                            ignoreDeclarationExceptions = "true")
                    , key = KEY_COURSE_SAVE)})
    public void receiveCourseSaveMessage(String json, Channel channel, Message message) throws IOException {
        log.info("课程添加：{}",json);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        //获得消息id
        String messageId = message.getMessageProperties().getHeader("spring_returned_message_correlation");
        log.info("消息id:{}",messageId);
        //执行setnx命令,以messageId为键保存数据,设置过期时间
        if(ops.setIfAbsent(messageId,"0",100, TimeUnit.SECONDS)){
            //如果返回true，则该键不存在，也就是没有重复消费
            //将消息转为课程，保存到es中
            Course course = JSON.parseObject(json,Course.class);
            courseService.saveOrUpdate(course);
            log.info("添加完成：{}",course);
            //修改messageId键的值为1，代表该消息已经被消费
            redisTemplate.opsForValue().set(messageId, "1",100,TimeUnit.SECONDS);
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }else{
            //返回false，代表存在该键
            String msg = (String) ops.get(messageId);
            log.info("该消息{}已经存在{}",messageId,msg);
            if("1".equals(msg)){
                //为1代表业务已经执行过了，手动确认结束
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        }
    }

    /**
     * 监听课程删除操作
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = QUEUE_COURSE_REMOVE, durable = "true"),
                    exchange = @Exchange(value = COURSE_EXCHANGE,
                            type = ExchangeTypes.TOPIC,
                            ignoreDeclarationExceptions = "true")
                    , key = KEY_COURSE_REMOVE)})
    public void receiveCourseDeleteMessage(Long id) {
        try {
            courseService.removeById(id);
            log.info("课程删除完成：{}",id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}