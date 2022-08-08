package com.gl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gl.mapper")
@SpringBootApplication
public class AdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdServiceApplication.class, args);
    }

}
