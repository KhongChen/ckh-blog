package com.ckh.blog.vo;

import java.io.Serializable;

public class IndexType implements Serializable {
    private Long id;
    private String name;
    private Integer blogNum;

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

    public Integer getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(Integer blogNum) {
        this.blogNum = blogNum;
    }

    @Override
    public String toString() {
        return "IndexType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogNum=" + blogNum +
                '}';
    }
}
