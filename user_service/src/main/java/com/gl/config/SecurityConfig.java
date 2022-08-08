package com.gl.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.dao.UserDao;
import com.gl.entity.Data;
import com.gl.entity.User;
import com.gl.utils.CookieUtil;
import com.gl.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.Arrays;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JdbcTokenRepositoryImpl jdbcTokenRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/images/**", "/upload/**", "/search/**","/user/refresh","/register","/course-upload","/checkUsername").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler((req, resp, e) -> { //配置登录失败的处理
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    Data data = new Data();
                    data.setState("300");
                    data.setMessage("账号或者密码错误");
                    Object o = JSON.toJSON(data);
                    String s = o.toString();
                    out.write(s);
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, authentication) -> {//注销成功的处理
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("注销成功");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> { //配置没有登录的处理
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write("尚未登录，请先登录");
                            out.flush();
                            out.close();
                        }
                );
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // 解决跨域问题
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE"));

        //  跨域的请求头
        configuration.addAllowedHeader("*"); // 2
        //  跨域的请求方法
        configuration.addAllowedMethod("*"); // 3

        // 允许携带cookie
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    /**
//     * 解决异步访问跨域
//     */
//    @Configuration
//    public class CorsConfig implements WebMvcConfigurer {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            //本应用的所有方法都会去处理跨域请求
//            registry.addMapping("/**")
//                    //允许远端访问的域名
//                    .allowedOrigins("http://localhost:8080")
//                    //允许请求的方法("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                    .allowedMethods("*")
//                    //允许请求头
//                    .allowedHeaders("*");
//        }
//
//    }
}