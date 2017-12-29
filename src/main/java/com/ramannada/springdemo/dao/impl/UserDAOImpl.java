package com.ramannada.springdemo.dao.impl;

import com.ramannada.springdemo.dao.UserDAO;
import com.ramannada.springdemo.entity.Authority;
import com.ramannada.springdemo.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {
    @Value("${table.user}")
    private String table;

//    public UserDAOImpl() {
//        table = tableUser;
//    }

    @Override
    public User save(User user) throws SQLException {
        final String sql = "INSERT INTO " + table + " (username, password) VALUES (?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public java.sql.PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                java.sql.PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());

                return statement;
            }
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        user.setId(id);

        return user;
    }

    @Override
    public User get(Long id) {
        String sql = "SELECT * FROM view_user_authorities WHERE id = ?";

        List<User> list;
        List<Authority> allAuthorityList = new ArrayList<>();
        try {
            list = jdbcTemplate.query(sql, new Object[] {id},new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    Authority authority = new Authority();
                    authority.setId(rs.getLong("id_authority"));
                    authority.setName(rs.getString("name_authority"));

                    List<Authority> authorityList = new ArrayList<>();
                    authorityList.add(authority);

                    User result = new User();
                    result.setId(rs.getLong("id"));
                    result.setUsername(rs.getString("username"));
                    result.setPassword(rs.getString("password"));
                    result.setEnabled(rs.getBoolean("enabled"));
                    result.setAuthorities(authorityList);

                    return result;
                }}
            );
        } catch (DataAccessException e) {
            return null;
        }

        if (list == null) {
            return null;
        }

        for(User a:list) {
            for (GrantedAuthority u: a.getAuthorities()) {
                allAuthorityList.add((Authority) u);
            }
        }

        if (allAuthorityList.size() > 1) {
            list.get(0).setAuthorities(allAuthorityList);

            return list.get(0);
        }

        return list.get(0);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> find(User user) {
        List<User> userList;
        String name = null;

        String sql = "SELECT * FROM " + table + " WHERE name LIKE ?";

        if (user.getUsername() != null) {
            name = "%" + user.getUsername() + "%";
        }

        userList = jdbcTemplate.query(sql, new Object[] {name}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User result = new User();
                result.setId(resultSet.getLong("id"));
                result.setUsername(resultSet.getString("username"));
                result.setPassword(resultSet.getString("password"));
                return result;
            }
        });

        return userList;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";

        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id FROM " + table + " WHERE username = ?";

        Long id = jdbcTemplate.queryForObject(sql, new Object[] {username}, Long.class);

        User response = get(id);

        if (response == null) {
            return null;
        }

        return response;
    }
}
