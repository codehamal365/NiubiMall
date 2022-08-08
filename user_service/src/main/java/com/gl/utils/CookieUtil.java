package com.gl.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {
    public static final String ENCODE = "UTF-8";

    // 保存Cookie
    public static void saveCookie(HttpServletResponse resp, String name, String value, int maxAge) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(URLEncoder.encode(name, ENCODE), URLEncoder.encode(value, ENCODE));
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }


    // 取到Cookie
    public static String getCookie(HttpServletRequest req,String name) throws UnsupportedEncodingException {
        Cookie[] cookies = req.getCookies();

            for (Cookie cookie : cookies) {
                if (name.equals(URLDecoder.decode(cookie.getName(), ENCODE))) {
                    return URLDecoder.decode(cookie.getValue(), ENCODE);
                }
            }


        return null;
    }
}