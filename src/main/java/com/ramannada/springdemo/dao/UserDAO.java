package com.ramannada.springdemo.dao;

import com.ramannada.springdemo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDAO extends BaseDAO<User>, UserDetailsService {
    User findByUsername(String username);
}
