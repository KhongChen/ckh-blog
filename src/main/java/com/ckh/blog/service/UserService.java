package com.ckh.blog.service;

import com.ckh.blog.pojo.User;

public interface UserService {

    User checkUser(String username, String password);

    int saveUser(User user);
}
