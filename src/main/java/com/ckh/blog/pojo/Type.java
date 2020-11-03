package com.ckh.blog.pojo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Type implements Serializable {

    private Long id;
    @NotBlank(message = "分类名称不能为空") //对字符串,且会去掉前后空格,检查是否为空
    private String name;

    private List<Blog> blogList = new ArrayList<>();

    public Type() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogList=" + blogList +
                '}';
    }
}
