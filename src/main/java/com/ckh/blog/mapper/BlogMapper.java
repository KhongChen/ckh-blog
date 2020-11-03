package com.ckh.blog.mapper;

import com.ckh.blog.pojo.Blog;
import com.ckh.blog.pojo.Tag;
import com.ckh.blog.pojo.Type;
import com.ckh.blog.vo.BlogQuery;
import com.ckh.blog.vo.DetailBlog;
import com.ckh.blog.vo.IndexBlog;
import com.ckh.blog.vo.RecommendBlog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogMapper {

    int saveBlog(Blog blog);

    int saveBlogTag(Map<String, Object> map);

    Blog getBlogById(Long id);

    DetailBlog getDetailBlog(Long id);

    List<IndexBlog> getIndexBlog();

    List<BlogQuery> getBlogList();

    List<BlogQuery> getBlogByCondition(BlogQuery blogQuery);

    List<IndexBlog> getBlogByType(Long id);

    List<IndexBlog> getBlogByTag(Long id);

    List<RecommendBlog> getBlogByRecommend();

    List<IndexBlog> getBlogByKeyWord(String keyword);

    List<Blog> getBlogByTime(String year);

    Integer getBlogTotal();

    List<String> findGroupYear();

    int deleteBlog(Long id);

    int deleteBlogTag(Map<String,Object> map);

    int updateBlog(Blog blog);

    int updateViews(Long id);


}
