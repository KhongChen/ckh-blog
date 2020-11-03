package com.ckh.blog.controller;

import com.ckh.blog.pojo.Type;
import com.ckh.blog.service.BlogService;
import com.ckh.blog.service.TypeService;
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
public class TypeShowController {

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String typeBlog(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        //所有分类
        List<Type> typeList = typeService.getTypeList();
        Long id = typeList.get(0).getId();
        model.addAttribute("typeList",typeList);
        //某分类的博客
        PageHelper.startPage(pageNum,3,"update_time desc");
        List<IndexBlog> typeBlogList = blogService.getBlogByType(id);
        PageInfo<IndexBlog> pageInfo = new PageInfo<>(typeBlogList);
        System.out.println(pageInfo);
        model.addAttribute("page", pageInfo);
        //传递选中的id返回给页面
        model.addAttribute("activeTypeId", id);
        return "types";
    }

    @GetMapping("/type/{id}/blogs")
    public String BlogByType(@PathVariable Long id, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        //所有分类
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
        //某分类的博客
        PageHelper.startPage(pageNum,3,"update_time desc");
        List<IndexBlog> typeBlogList = blogService.getBlogByType(id);
        PageInfo<IndexBlog> pageInfo = new PageInfo<>(typeBlogList);
        System.out.println(pageInfo);
        model.addAttribute("page", pageInfo);
        //传递选中的id返回给页面
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
