package com.ckh.blog.vo;

import java.io.Serializable;

public class RecommendBlog implements Serializable {

    private Long id;
    private String title;
    private boolean recommend;

    public RecommendBlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "RecommendBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
