package com.ywknight.blog.service;

import com.ywknight.blog.po.User;

public interface UserService {

    /**
     * 检验账户
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username, String password);
}
