package com.ckh.blog.vo;

import java.io.Serializable;
import java.util.Date;

public class ArchiveBlog implements Serializable {

    private Long id;
    private String title;
    private String flag;
    private Date updateTime;


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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ArchiveBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", flag='" + flag + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
