package com.ckh.blog.service;

import com.ckh.blog.mapper.TypeMapper;
import com.ckh.blog.pojo.Type;
import com.ckh.blog.vo.IndexType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    
    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public Type getTypeById(Long id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public List<Type> getTypeList() {
        return typeMapper.getTypeList();
    }

    @Override
    public List<IndexType> getTypeTop(Integer count) {
        return typeMapper.getTypeTop(count);
    }
}
