package com.ramannada.springdemo.dao.impl;

import com.ramannada.springdemo.dao.ImageDAO;
import com.ramannada.springdemo.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ImageDAOImpl extends BaseDAOImpl implements ImageDAO {
    @Value("${table.image}")
    private String table;

    @Override
    public Image save(Image image) throws SQLException {
        final String sql = "INSERT INTO " + table +
                " (name, thumbnailFilename, newFilename, contentType, size, thumbnailSize, " +
                "dateCreated, lastUpdated, url, thumbnailUrl, deleteUrl, " +
                "deleteType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, image.getName());
                statement.setString(2, image.getThumbnailFilename());
                statement.setString(3, image.getNewFilename());
                statement.setString(4, image.getContentType());
                statement.setLong(5, image.getSize());
                statement.setLong(6,image.getThumbnailSize());
                statement.setDate(7,image.getDateCreated());
                statement.setDate(8, image.getLastUpdated());
                statement.setString(9, image.getUrl());
                statement.setString(10, image.getThumbnailUrl());
                statement.setString(11, image.getDeleteUrl());
                statement.setString(12, image.getDeleteType());

                return statement;
            }
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        image.setId(id);

        return image;
    }

    @Override
    public Image get(Long id) {
        String sql = "SELECT * FROM " + table + " WHERE id = ?";
        Image image;
        try {
            image = jdbcTemplate.queryForObject(sql, new Object[] {id},new RowMapper<Image>() {

                @Override
                public Image mapRow(ResultSet rs, int i) throws SQLException {
                    Image result = new Image();
                    result.setId(rs.getLong("id"));
                    result.setName(rs.getString("name"));
                    result.setUrl(rs.getString("url"));
                    result.setThumbnailUrl(rs.getString("thumbnailUrl"));
                    return result;
                }}
            );
        } catch (DataAccessException e) {
            return null;
        }

        return image;
    }

    @Override
    public List<Image> getAll() {
        return null;
    }

    @Override
    public List<Image> find(Image image) {
        return null;
    }

    @Override
    public Image update(Image image) {
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";

        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
