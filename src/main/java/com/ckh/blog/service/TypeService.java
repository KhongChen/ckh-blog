package com.ckh.blog.service;

import com.ckh.blog.pojo.Type;
import com.ckh.blog.vo.IndexType;

import java.util.List;

public interface TypeService {

    int saveType(Type type);

    int deleteType(Long id);

    int  updateType(Type type);

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    List<Type> getTypeList();

    List<IndexType> getTypeTop(Integer count);


}
