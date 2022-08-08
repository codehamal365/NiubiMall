package com.gl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
public class RememberConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

//        jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }




}
