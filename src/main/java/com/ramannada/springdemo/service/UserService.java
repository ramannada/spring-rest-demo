package com.ramannada.springdemo.service;

import com.ramannada.springdemo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User>, UserDetailsService {
}
