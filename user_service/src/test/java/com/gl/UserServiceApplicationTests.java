package com.gl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gl.dao.UserDao;
import com.gl.entity.User;
import com.gl.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","zhangsan");
        User user = userDao.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    void testJwt(){
        String token = JwtUtils.buildJwt("usernma:zhangsan,password:protect");
        System.out.println(token);
    }

}
