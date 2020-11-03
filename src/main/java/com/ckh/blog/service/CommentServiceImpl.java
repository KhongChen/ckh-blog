package com.ckh.blog.service;

import com.ckh.blog.mapper.CommentMapper;
import com.ckh.blog.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    //存放迭代找出所有子代的集合
    private List<Comment> temReplyList = new ArrayList<>();


    @Override
    @Transactional
    public int saveComment(Comment comment) {
        //父评论的id
        Long parentCommentId = comment.getParentComment().getId();
        System.out.println(parentCommentId);
        //如果父评论的id为不是-1,则说明提交的评论为子评论
        if (parentCommentId!=-1) {
            comment.setParentComment(commentMapper.getCommentByParentCommentId(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    @Override
    public List<Comment> getCommentListByIdAndParentCommentNull(Long blogId) {
        List<Comment> commentList = commentMapper.getCommentListByIdAndParentCommentNull(blogId);
        return eachComment(commentList);
    }
    
    //循环遍历每一个顶级的评论节点,防止修改数据库
    private List<Comment> eachComment(List<Comment> commentList){
        List<Comment> new_commentList = new ArrayList<Comment>();
        for (Comment comment : commentList) {
            Comment c = new Comment();
            //拷贝
            BeanUtils.copyProperties(comment,c);
            new_commentList.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(new_commentList);
        return new_commentList;
    }

    //合并子评论到每一个父节点集合中
    private void combineChildren(List<Comment> commentList) {
        for (Comment comment: commentList) {
            //遍历父节点下的一级评论
            List<Comment> replyList = comment.getReplyComments();
            //循环遍历，找出子代，存放到temReplyList中
            for (Comment reply: replyList){
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(temReplyList);
            //清除临时存放区
            temReplyList = new ArrayList<>();
        }
    }

    //遍历子评论
    private void recursively(Comment comment) {
        temReplyList.add(comment);
        //如果一级评论下还有子级评论
        if (comment.getReplyComments().size()>0) {
            List<Comment> replyList = comment.getReplyComments();
            for (Comment reply : replyList) {
               recursively(reply);
            }
        }

    }
}
