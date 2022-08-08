package com.gl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gl.dao.UserDao;
import com.gl.entity.User;
import com.gl.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService  {
}
