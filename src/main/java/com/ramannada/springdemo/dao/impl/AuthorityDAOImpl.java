package com.ramannada.springdemo.dao.impl;

import com.ramannada.springdemo.dao.AuthorityDAO;
import com.ramannada.springdemo.entity.Authority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class AuthorityDAOImpl extends BaseDAOImpl implements AuthorityDAO {
    @Value("${table.authority}")
    private String table;

    @Override
    public Authority save(Authority authority) throws SQLException {
        String sql = "INSERT INTO " + table + " (name) VALUES (?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1,authority.getName());

                return statement;
            }
        }, keyHolder);

        Long key = keyHolder.getKey().longValue();

        if (key == null) {
            return null;
        }

        authority.setId(key);

        return authority;
    }

    @Override
    public Authority get(Long id) {
        String sql = "SELECT * FROM " + table + " WHERE id = ?";
        Authority authority;

        authority = jdbcTemplate.queryForObject(sql, new Object[] {id}, new RowMapper<Authority>() {
            @Override
            public Authority mapRow(ResultSet resultSet, int i) throws SQLException {
                Authority result = new Authority();
                result.setId(resultSet.getLong("id"));
                result.setName(resultSet.getString("name"));

                return result;
            }
        });

        if (authority == null) {
            return null;
        }

        return authority;
    }

    @Override
    public List<Authority> getAll() {
        return null;
    }

    @Override
    public List<Authority> find(Authority authority) {
        return null;
    }

    @Override
    public Authority update(Authority authority) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
