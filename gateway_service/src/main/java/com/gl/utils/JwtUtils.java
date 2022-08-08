package com.gl.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;


public class JwtUtils {
    // 过期时间 1小时
    public static final long EXPIRE = 1000 * 60 * 60;
    // APP Secret
    public static final String APP_SECRET = "747571569";

    public static String buildJwt(String userToken){
        // 创建builder对象
        JwtBuilder builder = Jwts.builder();

        // 第一部分 JWT头 header
        builder.setHeaderParam("alg","HS256");
        builder.setHeaderParam("typ","JWT");

        // 第二部分 有效载荷 Payload
        builder.setId(UUID.randomUUID().toString());    // 唯一身份标识
        builder.setSubject("userToken");     // 令牌主题
        builder.setIssuedAt(new Date());     // 令牌时间
        builder.setExpiration(new Date(System.currentTimeMillis() + EXPIRE));

        // 私有字段
        builder.claim("token",userToken);

        // 签名hash 通过加密算法 和 密钥 生成密文
        builder.signWith(SignatureAlgorithm.HS256,APP_SECRET);

        // 将三部分联系起来
        String token = builder.compact();

        return token;
    }

    public static Claims chechJwt(String jwtToken){
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        JwsHeader header = claimsJws.getHeader();
        Claims body = claimsJws.getBody();
        String signature = claimsJws.getSignature();
        return body;
    }

}