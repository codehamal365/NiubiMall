package com.gl.utils;

import com.gl.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * JWT工具类
 */
public class JwtUtil {

    public static final String JWT_KEY_ID = "id";
    public static final String JWT_KEY_USERNAME = "username";
    public static final String JWT_KEY_ICON = "icon";
    public static final String JWT_KEY_REALNAME = "realname";
    public static final int EXPIRE_MINUTES = 30;

    /**
     * 私钥加密token
     */
    public static String generateToken(String id, String username, String realname, String icon, PrivateKey privateKey, int expireMinutes) throws Exception {

        return Jwts.builder()
                .claim(JWT_KEY_ID, id)
                .claim(JWT_KEY_ICON, icon)
                .claim(JWT_KEY_USERNAME, username)
                .claim(JWT_KEY_REALNAME, realname)
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 从token解析用户
     *
     * @param token
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static User getUserInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String id = (String) body.get(JWT_KEY_ID);
        String username = (String) body.get(JWT_KEY_USERNAME);
        String icon = (String) body.get(JWT_KEY_ICON);
        String realname = (String) body.get(JWT_KEY_REALNAME);
        User user = new User(Integer.valueOf(id),username,null,realname,null,icon,0);
        return user;
    }
}