package com.ckh.blog.mapper;

import com.ckh.blog.pojo.Tag;
import com.ckh.blog.pojo.Type;
import com.ckh.blog.vo.IndexTag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TagMapper {

    int saveTag(Tag tag);

    int deleteTag(Long id);

    int  updateTag(Tag tag);

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    List<Tag> getTagList();

    List<Tag> getSelectTags(Map<String,Object> map);

    List<IndexTag> getTagTop(Integer count);
}
