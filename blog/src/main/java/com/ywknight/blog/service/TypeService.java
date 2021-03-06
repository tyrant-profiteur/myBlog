package com.ywknight.blog.service;

import com.ywknight.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> getTypeList();

    Type updateType(Long id,Type type);

    List<Type> listTypeTop(Integer size);

    void deleteType(Long id);
}
