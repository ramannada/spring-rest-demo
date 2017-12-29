package com.ramannada.springdemo.dao;

import com.ramannada.springdemo.entity.User;

public interface UserDAO extends BaseDAO<User> {
    User findByUsername(String username);
}
