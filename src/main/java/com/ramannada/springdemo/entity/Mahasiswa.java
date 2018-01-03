package com.ramannada.springdemo.entity;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Mahasiswa extends BaseEntity {
    @NotNull
    @Size(min = 8)
    private String nim;
    @NotNull
    @NotBlank
    @NotEmpty
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
