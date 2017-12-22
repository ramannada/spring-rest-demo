package com.ramannada.springdemo.dao;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<Entity> {
    Entity save(Entity entity) throws SQLException;

    Entity get(Long id);

    List<Entity> getAll();

    List<Entity> find(Entity entity);

    Entity update(Entity entity);

    void delete(Long id);
}