package com.ramannada.springdemo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public abstract class BaseDAOImpl {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected DataSource dataSource;
    private String table;

    public void delete(Long id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";

        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
