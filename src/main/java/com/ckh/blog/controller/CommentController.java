package com.ckh.blog.controller;

import com.ckh.blog.pojo.Comment;
import com.ckh.blog.pojo.User;
import com.ckh.blog.service.BlogService;
import com.ckh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;


    @PostMapping("/comment/submit")
    public String send(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlogById(blogId));
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar("/images/avatar.jpg");
        }
        commentService.saveComment(comment);
        System.out.println("提交的评论信息"+comment);
        return "redirect:/refresh/"+comment.getBlog().getId()+"/comments";
    }

    @GetMapping("/refresh/{id}/comments")
    public String refresh(@PathVariable Long id, Model model) {
        List<Comment> commentList = commentService.getCommentListByIdAndParentCommentNull(id);
        model.addAttribute("commentList",commentList);
        System.out.println("评论区信息:"+commentList);
        return "blog :: comment-field";
    }

    @GetMapping("/blog/{id}/comments")
    @ResponseBody
    public List<Comment> getCommentListById(@PathVariable Long id) {
        List<Comment> commentList = commentService.getCommentListByIdAndParentCommentNull(id);
        System.out.println("评论区信息:"+commentList);
        return commentList;
    }
}
