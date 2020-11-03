package com.ckh.blog.vo;

import com.ckh.blog.pojo.Type;

import java.io.Serializable;
import java.util.Date;

 /*
  * @Description: 后台搜索博客显示
  * @Author: ckh
  * @Date: 2020/9/11 21:27
  */
public class BlogQuery implements Serializable {

    private Long id;
    private String title;
    private Long typeId;
    private boolean recommend;
    private boolean published;
    private Date updateTime;
    private Type type;

    public BlogQuery() {
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

     public Long getTypeId() {
         return typeId;
     }

     public void setTypeId(Long typeId) {
         this.typeId = typeId;
     }

     public boolean isRecommend() {
         return recommend;
     }

     public void setRecommend(boolean recommend) {
         this.recommend = recommend;
     }

     public boolean isPublished() {
         return published;
     }

     public void setPublished(boolean published) {
         this.published = published;
     }

     public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

     public Type getType() {
         return type;
     }

     public void setType(Type type) {
         this.type = type;
     }

     @Override
     public String toString() {
         return "BlogQuery{" +
                 "id=" + id +
                 ", title='" + title + '\'' +
                 ", typeId=" + typeId +
                 ", recommend=" + recommend +
                 ", published=" + published +
                 ", updateTime=" + updateTime +
                 ", type=" + type +
                 '}';
     }
 }

