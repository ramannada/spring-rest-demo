package com.ramannada.springdemo.dao;

import com.ramannada.springdemo.entity.Mahasiswa;

import java.util.List;

public interface MahasiswaDAO extends BaseDAO<Mahasiswa> {
    List<Mahasiswa> getAllWithPage(int page, int entityPerPage);
}
