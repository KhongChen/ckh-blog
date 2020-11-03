package com.ckh.blog.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {


    //将application.yml配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource的同名参数中
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    //后台监控统计功能
    //springBoot内置了Servlet容器,没有web.xml文件，替代方法：ServletRegistrationBean
    //Druid内置提供了一个StatViewServlet用于展示Druid的统计信息
    public ServletRegistrationBean StatViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //后台界面账号密码配置
        Map<String, String> map = new HashMap<>();
        //键是固定的
        map.put("loginUsername","admin");
        map.put("loginPassword","123456");

        //权限设置
        //为空或者为null时，表示允许所有访问
        map.put("allow","");
        bean.setInitParameters(map); //初始化参数
        return bean;
    }


    @Bean
    //配置Druid监控过滤功能
    // WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
    public FilterRegistrationBean webStartFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        bean.addUrlPatterns("/*");
        Map<String, String> map = new HashMap<>();
        //配置参数
        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
        map.put("exclusions", "/css/webfonts/*,*.js,*.css,*.gif,*.jpg,*.png,*.ico,/druid/*");
        //过滤请求
        bean.setInitParameters(map);
        return bean;
    }
}
