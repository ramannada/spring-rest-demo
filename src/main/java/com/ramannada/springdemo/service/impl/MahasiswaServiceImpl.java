package com.ramannada.springdemo.service.impl;

import com.ramannada.springdemo.dao.MahasiswaDAO;
import com.ramannada.springdemo.entity.Mahasiswa;
import com.ramannada.springdemo.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service("mahasiswaService")
@Transactional(readOnly = true)
public class MahasiswaServiceImpl implements MahasiswaService {
    @Autowired
    private MahasiswaDAO mahasiswaDao;

    @Transactional
    @Override
    public Mahasiswa save(Mahasiswa mahasiswa) {
        Mahasiswa response = null;
        try {
            response = mahasiswaDao.save(mahasiswa);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Transactional
    @Override
    public Mahasiswa get(Long id) {
        return mahasiswaDao.get(id);
    }

    @Transactional
    @Override
    public List<Mahasiswa> getAll() {
        return mahasiswaDao.getAll();
    }

    @Transactional
    @Override
    public List<Mahasiswa> getAllWithPage(int page, int entityPerPage) {
        return mahasiswaDao.getAllWithPage(page, entityPerPage);
    }

    @Override
    public Boolean isExist(Long id) {
        Mahasiswa mahasiswa = mahasiswaDao.get(id);

        if (mahasiswa != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Mahasiswa> find(Mahasiswa mahasiswa) {
        return mahasiswaDao.find(mahasiswa);
    }

    @Transactional
    @Override
    public Mahasiswa update(Mahasiswa mahasiswa) {
        return mahasiswaDao.update(mahasiswa);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        mahasiswaDao.delete(id);
    }
}
