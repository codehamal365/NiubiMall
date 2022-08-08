package com.gl.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gl.dao.UserDao;
import com.gl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userDao.selectOne(queryWrapper);
        if (user==null){
            throw new UsernameNotFoundException("用户名找不到");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList("default".toString()));
    }
}
