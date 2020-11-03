package com.ckh.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void test() {
        //获取redis的连接对象
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();


        System.out.println(redisConnection);

        redisTemplate.opsForValue().set("username","ckh");
        System.out.println(redisTemplate.opsForValue().get("username"));
    }
}
