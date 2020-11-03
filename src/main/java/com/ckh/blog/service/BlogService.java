package com.ckh.blog.service;

import com.ckh.blog.pojo.Blog;
import com.ckh.blog.vo.BlogQuery;
import com.ckh.blog.vo.DetailBlog;
import com.ckh.blog.vo.IndexBlog;
import com.ckh.blog.vo.RecommendBlog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    int saveBlog(Blog blog,String tagIds);

    int saveBlogTag(Map<String, Object> map);

    Blog getBlogById(Long id);

    DetailBlog getDetailBlog(Long id);

    DetailBlog getAndConvert(Long id);

    List<IndexBlog> getIndexBlog();

    List<BlogQuery> getBlogList();

    List<BlogQuery> getBlogByCondition(BlogQuery blogQuery);

    List<IndexBlog> getBlogByType(Long id);

    List<IndexBlog> getBlogByTag(Long id);

    List<RecommendBlog> getBlogByRecommend();

    List<IndexBlog> getBlogByKeyWord(String keyword);

    Map<String,List<Blog>> getBlogByTime();

    Integer getBlogTotal();

    int deleteBlog(Long id);

    int updateBlog(Blog blog,String tagIds);

}
