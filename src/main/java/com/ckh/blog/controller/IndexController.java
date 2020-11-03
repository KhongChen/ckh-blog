package com.ckh.blog.controller;

import com.ckh.blog.pojo.Comment;
import com.ckh.blog.pojo.Type;
import com.ckh.blog.service.BlogService;
import com.ckh.blog.service.CommentService;
import com.ckh.blog.service.TagService;
import com.ckh.blog.service.TypeService;
import com.ckh.blog.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @GetMapping({"/index", "/"})
    public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        //博客列表
        PageHelper.startPage(pageNum,4,"update_time desc");
        List<IndexBlog> blogList = blogService.getIndexBlog();
        System.out.println("博客列表:"+ blogList);
        PageInfo<IndexBlog> pageInfo = new PageInfo<IndexBlog>(blogList);
        model.addAttribute("page",pageInfo);
        //分类排序
        List<IndexType> typeTop = typeService.getTypeTop(4);
        System.out.println(typeTop);
        model.addAttribute("typeList",typeTop);
        //标签排序
        List<IndexTag> tagTop = tagService.getTagTop(5);
        System.out.println(tagTop);
        model.addAttribute("tagList", tagTop);
        //最新推荐
        List<RecommendBlog> recommendBlogList = blogService.getBlogByRecommend();
        System.out.println(recommendBlogList);
        model.addAttribute("recommendBlogList",recommendBlogList);
        return "index";
    }

    @GetMapping("/blog/{id}/info")
    public String info(@PathVariable Long id, Model model) {
        DetailBlog blog = blogService.getAndConvert(id);
        System.out.println(blog);
        List<Comment> commentList = commentService.getCommentListByIdAndParentCommentNull(id);
        System.out.println(commentList);
        if (commentList.isEmpty()) {
            System.out.println("评论为空");
            model.addAttribute("msg","目前暂无评论信息");
        }else {
            model.addAttribute("commentList",commentList);
        }
        model.addAttribute("blog", blog);
        return "blog";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam String keyword, Model model){
        PageHelper.startPage(pageNum,5);
        //搜索
        List<IndexBlog> blogList = blogService.getBlogByKeyWord(keyword);
        PageInfo<IndexBlog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("page", pageInfo);
        //返回关键词，搜索栏显示关键词
        model.addAttribute("keyword",keyword);
        return "search";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
