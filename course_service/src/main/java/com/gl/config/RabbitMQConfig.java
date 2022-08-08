package com.gl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ的配置
 */
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_COURSE_SAVE = "queue.course.save";
    public static final String QUEUE_COURSE_REMOVE = "queue.course.remove";
    public static final String KEY_COURSE_SAVE = "key.course.save";
    public static final String KEY_COURSE_REMOVE = "key.course.remove";
    public static final String COURSE_EXCHANGE = "edu.course.exchange";

    @Bean
    public Queue queueCourseSave() {
        return new Queue(QUEUE_COURSE_SAVE);
    }

    @Bean
    public Queue queueCourseRemove() {
        return new Queue(QUEUE_COURSE_REMOVE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(COURSE_EXCHANGE);
    }

    @Bean
    public Binding bindCourseSave() {
        return BindingBuilder.bind(queueCourseSave()).to(topicExchange()).with(KEY_COURSE_SAVE);
    }

    @Bean
    public Binding bindCourseRemove() {
        return BindingBuilder.bind(queueCourseRemove()).to(topicExchange()).with(KEY_COURSE_REMOVE);
    }
}