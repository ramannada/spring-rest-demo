package com.ramannada.springdemo.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class Authority extends BaseEntity implements GrantedAuthority{
    private String name;
    private List<User> user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
