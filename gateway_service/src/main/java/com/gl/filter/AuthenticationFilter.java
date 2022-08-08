package com.gl.filter;

import com.gl.entity.GatewayConfig;
import com.gl.entity.User;
import com.gl.utils.JwtUtil;
import com.gl.utils.JwtUtils;
import com.gl.utils.RsaUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * 对所有请求进行拦截，放行登录成功的请求
 */
@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private GatewayConfig gatewayConfig;

    private static final Integer EXPIRE_DATE = 60 * 30;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //放行不拦截的请求
        List<String> whiteList = gatewayConfig.getWhiteList();
        for (String str : whiteList) {
            if (request.getURI().getPath().contains(str)) {
                log.info("放行 {}",request.getURI().getPath());
                return chain.filter(exchange);
            }
        }
        try {
            String token = request.getHeaders().getFirst("Authorization");
//            //读取cookie中的token
//            String token = request.getCookies().getFirst("token").getValue();
            //解析该token为用户对象
           /* System.out.println(token);
            if(token != "" || token!=null){
                User user = JwtUtil.getUserInfoFromToken(token, RsaUtil.publicKey);
                return chain.filter(exchange);
            }

            log.info("登录成功！{}" , "user");*/
        } catch (Exception e) {
            log.error("{}请求被拦截",request.getURI().getPath(),e);
            //拦截请求
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String msg = "Request Denied!!";
            DataBuffer wrap = response.bufferFactory().wrap(msg.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        //放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}