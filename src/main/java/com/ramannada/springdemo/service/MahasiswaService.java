package com.ramannada.springdemo.service;

import com.ramannada.springdemo.entity.Mahasiswa;

import java.util.List;

public interface MahasiswaService extends BaseService<Mahasiswa> {
    List<Mahasiswa> getAllWithPage(int page, int entitiyPerPage);
}
