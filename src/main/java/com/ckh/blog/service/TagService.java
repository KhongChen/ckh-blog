package com.ckh.blog.service;

import com.ckh.blog.pojo.Tag;
import com.ckh.blog.pojo.Type;
import com.ckh.blog.vo.IndexTag;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TagService {

    int saveTag(Tag tag);

    int deleteTag(Long id);

    int  updateTag(Tag tag);

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    List<Tag> getTagList();

    List<Tag> getSelectTags(String tagIds);

    List<IndexTag> getTagTop(Integer count);
}
