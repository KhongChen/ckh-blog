package com.ckh.blog.service;

import com.ckh.blog.pojo.Comment;

import java.util.List;

public interface CommentService {

    int saveComment(Comment comment);

    List<Comment> getCommentListByIdAndParentCommentNull(Long blogId);
}
