package com.ramannada.springdemo.entity;


public class BaseEntity implements Entity {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
