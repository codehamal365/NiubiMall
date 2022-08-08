package com.gl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gl.entity.Course;
import com.gl.entity.UserCourseOrder;
import com.gl.mapper.OrderMapper;
import com.gl.feign.CourseFeignClient;
import com.gl.service.OrderService;
import com.gl.utlils.OrderIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, UserCourseOrder> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CourseFeignClient courseFeignClient;


    @Override
    // 生成订单
    public UserCourseOrder geneOrder(Integer courseId,Integer userId) {
        // 通过课程服务 查询课程信息
        Course course = courseFeignClient.selectCourseById(courseId);
        // 生成订单
        UserCourseOrder order = new UserCourseOrder();
        long time = new Date().getTime();
        Timestamp timestamp = new Timestamp(time);
        order.setUserId(userId);
        order.setCourseId(courseId);
        order.setStatus(0);
        order.setCreateTime(timestamp);
        order.setUpdateTime(timestamp);
        order.setIsDel(0);
        // 生成随机交易单号
        String tradeNo = OrderIdUtil.getOrderId();
        order.setTradeNo(tradeNo);
        order.setPayTypeId(3);
        int price = Integer.valueOf(course.getDiscounts().intValue());
        order.setPrice(price);
        order.setCourseId(courseId);
        System.out.println(order);
        // 保存订单到数据库
        orderMapper.insertOrder(order);
        return order;
    }
}
