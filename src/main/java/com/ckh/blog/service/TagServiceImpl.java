package com.ckh.blog.service;

import com.ckh.blog.mapper.TagMapper;
import com.ckh.blog.pojo.Tag;
import com.ckh.blog.vo.IndexTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }

    @Override
    public List<Tag> getSelectTags(String tagIds) {
        //字符串数组转为list
        List<Long> listIds = Arrays.asList(tagIds.split(","))
                .stream()
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        System.out.println(listIds);
        Map<String, Object> map= new HashMap<>();
        map.put("tagIds", listIds);
        return tagMapper.getSelectTags(map);
    }

    @Override
    public List<IndexTag> getTagTop(Integer count) {
        return tagMapper.getTagTop(count);
    }
}
