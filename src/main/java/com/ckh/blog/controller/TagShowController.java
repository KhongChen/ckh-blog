package com.ckh.blog.controller;

import com.ckh.blog.pojo.Tag;
import com.ckh.blog.pojo.Type;
import com.ckh.blog.service.BlogService;
import com.ckh.blog.service.TagService;
import com.ckh.blog.vo.IndexBlog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;


    @GetMapping("/tags")
    public String tagBlog(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        //所有标签
        List<Tag> tagList = tagService.getTagList();
        Long id = tagList.get(0).getId();
        model.addAttribute("tagList",tagList);
        //某分类的博客
        PageHelper.startPage(pageNum,3,"update_time desc");
        List<IndexBlog> tagBlogList = blogService.getBlogByTag(id);
        PageInfo<IndexBlog> pageInfo = new PageInfo<>(tagBlogList);
        System.out.println("标签博客:"+pageInfo);
        model.addAttribute("page", pageInfo);
        //传递选中的id返回给页面
        model.addAttribute("activeTagId", id);
        return "tags";
    }

    @GetMapping("/tag/{id}/blogs")
    public String BlogByTag(@PathVariable Long id, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        //所有标签
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("tagList",tagList);
        //某分类的博客
        PageHelper.startPage(pageNum,3,"update_time desc");
        List<IndexBlog> tagBlogList = blogService.getBlogByTag(id);
        PageInfo<IndexBlog> pageInfo = new PageInfo<>(tagBlogList);
        System.out.println("标签博客:"+pageInfo);
        model.addAttribute("page", pageInfo);
        //传递选中的id返回给页面
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
