package com.ywknight.blog.service;

import com.ywknight.blog.po.Blog;
import com.ywknight.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    /**
     * 动态查询分页
     * @param pageable
     * @param blog
     * @return
     */
    Page<Blog> ListBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
