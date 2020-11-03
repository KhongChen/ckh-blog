package com.ckh.blog.mapper;

import com.ckh.blog.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    int saveUser(User user);
}
