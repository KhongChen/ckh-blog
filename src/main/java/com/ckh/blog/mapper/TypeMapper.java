package com.ckh.blog.mapper;

import com.ckh.blog.pojo.Type;
import com.ckh.blog.vo.IndexType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeMapper {

    int saveType(Type type);

    int deleteType(Long id);

    int  updateType(Type type);

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    List<Type> getTypeList();

    List<IndexType> getTypeTop(Integer count);
}
