package com.ckh.blog.controller.admin;

import com.ckh.blog.pojo.Blog;
import com.ckh.blog.pojo.Tag;
import com.ckh.blog.pojo.User;
import com.ckh.blog.service.BlogService;

import com.ckh.blog.service.TagService;
import com.ckh.blog.service.TypeService;
import com.ckh.blog.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //博客列表
    @GetMapping("/blog/List")
    public String blogList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 5, "update_time desc");
        List<BlogQuery> blogList = blogService.getBlogList();
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(blogList);
        System.out.println(pageInfo.toString());
        model.addAttribute("page", pageInfo);
        model.addAttribute("typeList", typeService.getTypeList());
        return "admin/blog/List";
    }

    //搜索博客列表
    @PostMapping("/blog/search")
    public String blogSearch(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, BlogQuery blogQuery,Model model) {
        System.out.println(blogQuery);
        PageHelper.startPage(pageNum, 5, "update_time desc");
        List<BlogQuery> blogList = blogService.getBlogByCondition(blogQuery);
        if (blogList.isEmpty()) {
            model.addAttribute("msg", "搜索结果为空");
        }else {
            PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(blogList);
            model.addAttribute("page",pageInfo);
            System.out.println(pageInfo);
        }
        return "admin/blog/search :: table-field";
    }

    //添加博客页面
    @GetMapping("/blog/input")
    public String blogInput(Model model) {
        model.addAttribute("typeList", typeService.getTypeList());
        model.addAttribute("tagList", tagService.getTagList());
        return "admin/blog/input";
    }

    //跳转到修改页面
    @GetMapping("/blog/{id}/input")
    public String blogEditInput(@PathVariable Long id, Model model) {
        Blog blog= blogService.getBlogById(id);
        System.out.println("博客内容:"+ blog);
        model.addAttribute("typeList", typeService.getTypeList());
        model.addAttribute("tagList", tagService.getTagList());
        model.addAttribute("blog", blog);
        //博客标签组
        List<Long> tagList = new ArrayList<>();
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
             tagList.add(tag.getId());
        }
        model.addAttribute("blog_tags", tagList);
        return "admin/blog/update-input";
    }

    //添加博客
    @PostMapping("/blog/add")
    public String blogAdd(Blog blog, String tagIds, RedirectAttributes attributes, HttpSession session) {
        System.out.println(tagIds);
        //设置blog的user
        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置blog的tag
        blog.setTags(tagService.getSelectTags(tagIds));
        //插入博客
        int result = blogService.saveBlog(blog, tagIds);
        if (result > 0) {
            attributes.addFlashAttribute("msg", "操作成功");
        } else {
            attributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/admin/blog/List";
    }

    @GetMapping("/blog/{id}/delete")
    public String blogDelete(@PathVariable Long id, RedirectAttributes attributes) {
        int result = blogService.deleteBlog(id);
        if (result > 0) {
            attributes.addFlashAttribute("msg", "删除成功");
        } else {
            attributes.addFlashAttribute("msg", "删除失败");
        }
        return "redirect:/admin/blog/List";
    }

    @PostMapping("/blog/update")
    public String blogUpdate(Blog blog, String tagIds, HttpSession session,RedirectAttributes attributes) {
        System.out.println(tagIds);
        //设置blog的user
        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置blog的tag
        blog.setTags(tagService.getSelectTags(tagIds));
        int result = blogService.updateBlog(blog,tagIds);
        if (result > 0) {
            attributes.addFlashAttribute("msg", "更新成功");
        } else {
            attributes.addFlashAttribute("msg", "更新失败");
        }
        return "redirect:/admin/blog/List";
    }
}
