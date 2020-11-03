package com.ckh.blog.controller;

import com.ckh.blog.pojo.Blog;
import com.ckh.blog.service.BlogService;
import com.ckh.blog.vo.IndexBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        Map<String, List<Blog>> blogList = blogService.getBlogByTime();
        model.addAttribute("total",blogService.getBlogTotal());
        model.addAttribute("archivesMap",blogList);
        return "archives";
    }
}
