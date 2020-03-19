package com.ywknight.blog.dao;

import com.ywknight.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {
    //根据名字查找分类
    Type findByName(String name);
}
