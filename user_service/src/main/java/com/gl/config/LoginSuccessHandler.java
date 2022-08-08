package com.gl.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.dao.UserDao;
import com.gl.entity.User;
import com.gl.entity.UserVO;
import com.gl.utils.CookieUtil;
import com.gl.utils.JwtUtil;
import com.gl.utils.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功的处理
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDao userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        try {
            //读取用户的其它信息
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",authentication.getName());
            User userObj = userService.selectOne(queryWrapper);
            //将用户名转换为JWT
            String token = JwtUtil.generateToken(userObj.getId().toString(),userObj.getUsername(),userObj.getRealname(),
                    userObj.getIcon(), RsaUtil.privateKey, JwtUtil.EXPIRE_MINUTES);
//            //保存到Cookie中
            CookieUtil.saveCookie(resp,"userToken",token,7 * 24 * 3600);
            resp.setContentType("application/json;charset=utf-8");
            //发送用户信息到前端
            PrintWriter out = resp.getWriter();
            UserVO userVO = new UserVO(userObj,token);
            out.write(new ObjectMapper().writeValueAsString(userVO));
            out.flush();
            out.close();
            log.info("生成token保存-->{}" , userVO);
        } catch (Exception e) {
            log.error("保存token失败",e);
        }
    }
}