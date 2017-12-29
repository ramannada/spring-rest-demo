package com.ramannada.springdemo.service.impl;

import com.ramannada.springdemo.dao.UserDAO;
import com.ramannada.springdemo.entity.User;
import com.ramannada.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public User save(User user) throws SQLException {
        User response = null;
        try {
            response = userDAO.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public User get(Long id) {
        return userDAO.get(id);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Boolean isExist(Long id) {
        return null;
    }

    @Override
    public List<User> find(User entity) {
        return userDAO.find(entity);
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = new User();

        user = userDAO.findByUsername(s);

        if (user == null) {
            throw  new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
