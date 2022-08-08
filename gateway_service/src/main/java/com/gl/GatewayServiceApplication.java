package com.gl;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public static BeanFactoryPostProcessor removeTomcatWebServerCustomizer() {
        return (beanFactory) ->
                ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition("tomcatWebServerFactoryCustomizer");

    }
}
