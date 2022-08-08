package com.gl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gl.dao.UserDao;
import com.gl.entity.User;
import com.gl.service.UserService;
import com.gl.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/course-upload")
    public ResponseEntity<String> upload(MultipartFile file) throws IOException {
        //创建文件输入流
        InputStream inputStream = file.getInputStream();
        //获得文件名
        String filename = file.getOriginalFilename();
        //调用文件上传方法
        OSSUtil.upload(inputStream,filename);
        //回调上传的文件
        String url = OSSUtil.getURL(filename);
        //返回前端
        return ResponseEntity.ok(url);
    }


    @GetMapping("updateUser")
    public ResponseEntity<String> updateUser(@RequestParam("userid") Integer userid,
                                             @RequestParam("newName") String newName,
                                             @RequestParam("imgUrl") String imgUrl){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",userid);
        User user = new User();
        user.setUsername(newName);
        user.setIcon(imgUrl);
        userService.update(user,wrapper);
        return ResponseEntity.ok("ok");
    }



    @GetMapping("checkUsername")
    public ResponseEntity<String> checkUsername(@RequestParam("newName") String newName){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",newName);
        User user = userDao.selectOne(wrapper);
        if (user == null){
            return ResponseEntity.ok("ok");
        }else {
            return ResponseEntity.ok("error");
        }
    }

    @GetMapping("updatePassword")
    public ResponseEntity<String> checkUsername(@RequestParam("userid") Integer userid,
                                                @RequestParam("newPwd") String newPwd,
                                                @RequestParam("oldPwd") String oldPwd){
        // 查询当前要修改用户的密码
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",userid);
        User user = userDao.selectOne(wrapper);
        // 比对原密码输入是否正确
        if (user != null){
            boolean isTure = bCryptPasswordEncoder.matches(oldPwd, user.getPassword());
            // 如果原密码输入成功，修改密码
            if (isTure){
                // 对新密码加密
                String newPassword = bCryptPasswordEncoder.encode(newPwd);
                // 更新密码
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",userid);
                User updateUser = new User();
                updateUser.setPassword(newPassword);
                userService.update(updateUser,wrapper);
                return ResponseEntity.ok("ok");
            }else{
                return ResponseEntity.ok("error");
            }
        }else {
            return ResponseEntity.ok("error");
        }

    }



    @GetMapping("register")
    public ResponseEntity<String> register(@RequestParam("username") String username,
                                           @RequestParam("password") String password,
                                           @RequestParam("realname") String realname,
                                           @RequestParam("telephone") String telephone,
                                           @RequestParam("newImgUrl") String newImgUrl){
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRealname(realname);
        user.setTelephone(telephone);
        user.setIcon(newImgUrl);
        user.setState(0);
        boolean isOk = userService.save(user);
        if (isOk){
            return ResponseEntity.ok("ok");
        }else {
            return ResponseEntity.ok("error");
        }

    }

}
