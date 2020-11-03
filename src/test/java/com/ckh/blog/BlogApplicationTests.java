package com.ckh.blog;

import com.alibaba.druid.pool.DruidDataSource;
import com.ckh.blog.pojo.Blog;
import com.ckh.blog.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    private BlogService blogService;

    @Test
    void testConnect() throws SQLException {
        //查看默认的数据源：class com.zaxxer.hikari.HikariDataSource
        System.out.println(dataSource.getClass());
        //获得数据库连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
        connection.close();
    }

    @Test
    void testBlog(){
        blogService.getBlogList().forEach(System.out::println);
    }

    @Test
    void testBlogById(){
        Blog blog = blogService.getBlogById(1L);
        System.out.println(blog);
    }

}
