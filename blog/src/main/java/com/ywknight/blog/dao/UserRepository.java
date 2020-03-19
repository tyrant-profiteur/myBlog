package com.ywknight.blog.dao;

import com.ywknight.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * user数据库操作
 */
public interface UserRepository extends JpaRepository<User,Long> {

//    根据用户名和密码查找用户
    User findByUsernameAndPassword(String username,String password);
}
