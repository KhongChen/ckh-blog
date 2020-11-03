package com.ckh.blog.service;

import com.ckh.blog.exception.NotFoundException;
import com.ckh.blog.mapper.BlogMapper;
import com.ckh.blog.pojo.Blog;
import com.ckh.blog.pojo.Tag;
import com.ckh.blog.utils.MarkDownUtils;
import com.ckh.blog.vo.BlogQuery;
import com.ckh.blog.vo.DetailBlog;
import com.ckh.blog.vo.IndexBlog;
import com.ckh.blog.vo.RecommendBlog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public int saveBlog(Blog blog,String tagIds) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        System.out.println("新增的博客:"+blog);
        blogMapper.saveBlog(blog);
        Map<String, Object> map= new HashMap<String,Object>();
        //获得标签值
        List<Long> listIds = Arrays.asList(tagIds.split(","))
                .stream()
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        //插入博客后获得其主键id
        map.put("blog_id", blog.getId());
        map.put("tagIds",listIds);
        //插入中间表
        int result =blogMapper.saveBlogTag(map);
        return result;
    }

    @Transactional
    @Override
    public int saveBlogTag(Map<String, Object> map) {
        return blogMapper.saveBlogTag(map);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public DetailBlog getDetailBlog(Long id) {
        return blogMapper.getDetailBlog(id);
    }

    //将markDown转为html
    @Transactional
    @Override
    public DetailBlog getAndConvert(Long id) {
        DetailBlog detailBlog = blogMapper.getDetailBlog(id);
        if (detailBlog==null) {
            throw new NotFoundException("该博客不存在");
        }
        //新建一个新的DetailBlog对象并复制，防止操作数据库
        DetailBlog blog = new DetailBlog();
        //作用:复制同名且同类型的字段
        BeanUtils.copyProperties(detailBlog,blog);
        String content = detailBlog.getContent();
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        blogMapper.updateViews(id);
        return blog;
    }

    @Override
    public List<IndexBlog> getIndexBlog() {
        return blogMapper.getIndexBlog();
    }

    @Override
    public List<BlogQuery> getBlogList() {
        return blogMapper.getBlogList();
    }

    @Override
    public List<BlogQuery> getBlogByCondition(BlogQuery blogQuery) {
        return blogMapper.getBlogByCondition(blogQuery);
    }

    @Override
    public List<IndexBlog> getBlogByType(Long id) {
        return blogMapper.getBlogByType(id);
    }

    @Override
    public List<IndexBlog> getBlogByTag(Long id) {
        return blogMapper.getBlogByTag(id);
    }

    @Override
    public List<RecommendBlog> getBlogByRecommend() {
        return blogMapper.getBlogByRecommend();
    }

    @Override
    public List<IndexBlog> getBlogByKeyWord(String keyword) {
        return blogMapper.getBlogByKeyWord(keyword);
    }

    @Override
    public Map<String,List<Blog>> getBlogByTime() {
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        List<String> groupYear = blogMapper.findGroupYear();
        for (String year: groupYear) {
            List<Blog> blogList= blogMapper.getBlogByTime(year);
            map.put(year,blogList);
        }
        return map;
    }

    @Override
    public Integer getBlogTotal() {
        return blogMapper.getBlogTotal();
    }

    @Transactional
    @Override
    public int deleteBlog(Long id) {
        return blogMapper.deleteBlog(id);
    }

    @Transactional
    @Override
    /*
    * @param: tagIds:标签组id
    * */
    public int updateBlog(Blog blog,String tagIds) {
        blog.setUpdateTime(new Date());
        System.out.println("更新的博客:"+blog);
        blogMapper.updateBlog(blog);
        Map<String, Object> map= new HashMap<String,Object>();
        //获得标签值
        List<Long> listIds = Arrays.asList(tagIds.split(","))
                .stream()
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        map.put("blog_id", blog.getId());
        map.put("tagIds", listIds);
        //删除原有标签
        blogMapper.deleteBlogTag(map);
        //重新中间表的tag
        int result = blogMapper.saveBlogTag(map);
        return result;
    }
}
