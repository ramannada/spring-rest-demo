package com.ramannada.springdemo.dao.impl;
import com.ramannada.springdemo.dao.MahasiswaDAO;
import com.ramannada.springdemo.entity.Mahasiswa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource({"classpath:config.properties"})
@Repository
public class MahasiswaDAOImpl implements MahasiswaDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;

    @Value("${table.mahasiswa}")
    private String table;


    @Override
    public Mahasiswa save(Mahasiswa mahasiswa) throws SQLException{
        final String sql = "INSERT INTO " + table + " (nim, name) VALUES (?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, mahasiswa.getNim());
                statement.setString(2, mahasiswa.getNama());

                return statement;
            }
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        mahasiswa.setId(id);

        return mahasiswa;
    }

    @Override
    public Mahasiswa get(Long id) {
        String sql = "SELECT * FROM " + table + " WHERE id = ?";
        Mahasiswa mahasiswa;
       try {
           mahasiswa = jdbcTemplate.queryForObject(sql, new Object[] {id},new RowMapper<Mahasiswa>() {

               @Override
               public Mahasiswa mapRow(ResultSet rs, int i) throws SQLException {
                   Mahasiswa result = new Mahasiswa();
                   result.setId(rs.getLong("id"));
                   result.setNim(rs.getString("nim"));
                   result.setNama(rs.getString("name"));
                   return result;
               }}
           );
       } catch (DataAccessException e) {
           return null;
       }

    	return mahasiswa;
    }

    @Override
    public List<Mahasiswa> getAll() {
        String sql = "SELECT * FROM " + table;
        List<Mahasiswa> mahasiswaList = new ArrayList<>();

        try {
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(resultSet.getLong("id"));
                mahasiswa.setNim(resultSet.getString("nim"));
                mahasiswa.setNama(resultSet.getString("name"));

                mahasiswaList.add(mahasiswa);
            }
        } catch (SQLException e) {
            return mahasiswaList;
        }


        return mahasiswaList;
    }

    @Override
    public List<Mahasiswa> find(Mahasiswa mahasiswa) {
        List<Mahasiswa> mahasiswaList;
        String name = null;
        
        String sql = "SELECT * FROM " + table + " WHERE name LIKE ?";

        if (mahasiswa.getNama() != null) {
            name = "%" + mahasiswa.getNama() + "%";
        }

        mahasiswaList = jdbcTemplate.query(sql, new Object[] {name}, new RowMapper<Mahasiswa>() {
                                            @Override
                                            public Mahasiswa mapRow(ResultSet resultSet, int i) throws SQLException {
                                                Mahasiswa result = new Mahasiswa();
                                                result.setId(resultSet.getLong("id"));
                                                result.setNim(resultSet.getString("nim"));
                                                result.setNama(resultSet.getString("name"));
                                                return result;
                                            }
                                        });


        return mahasiswaList;
    }

    @Override
    public Mahasiswa update(Mahasiswa mahasiswa) {
    	String sql = "UPDATE " + table + " SET nim = ?, name = ? WHERE id = ?";
    	int response = jdbcTemplate.update(sql, mahasiswa.getNim(), mahasiswa.getNama(), mahasiswa.getId());

    	if (response > 0) {
    	    return get(mahasiswa.getId());
        } else {
    	    return null;
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";

        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement preparedStatement =
//                        dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                preparedStatement.setString(1, mahasiswa.getNim());
//                preparedStatement.setString(2, mahasiswa.getNama());
//                return preparedStatement;
//            }
//        }, keyHolder);


