package com.ramannada.springdemo.service;

import com.ramannada.springdemo.entity.Entity;
import com.ramannada.springdemo.entity.Mahasiswa;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T extends Entity> {
    T save(T entity) throws SQLException;

    T get(Long id);

    List<T> getAll();

    Boolean isExist(Long id);

    List<T> find(T entity);

    T update(T entity);

    void delete(Long id);
}
