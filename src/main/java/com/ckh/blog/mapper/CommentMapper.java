package com.ckh.blog.mapper;

import com.ckh.blog.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    int saveComment(Comment comment);

    List<Comment> getCommentListByIdAndParentCommentNull(@Param("blogId") Long id);

    Comment getCommentByParentCommentId(Long id);
}
