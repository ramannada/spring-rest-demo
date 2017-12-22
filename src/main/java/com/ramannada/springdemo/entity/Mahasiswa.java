package com.ramannada.springdemo.entity;


public class Mahasiswa extends BaseEntity {
    private String nim;
    private String nama;

//    required by ormlite
    public Mahasiswa(){};

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim.trim();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama.trim();
    }
}
