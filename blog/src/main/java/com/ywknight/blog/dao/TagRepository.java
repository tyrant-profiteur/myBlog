package com.ywknight.blog.dao;

import com.ywknight.blog.po.Tag;
import com.ywknight.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    //根据名字查找分类
    Tag findByName(String name);
}
