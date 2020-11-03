package com.ckh.blog.service;

import com.ckh.blog.pojo.User;
import com.ckh.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public int saveUser(User user) {
        return userMapper.saveUser(user);
    }
}
