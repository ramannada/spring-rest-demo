package com.ramannada.springdemo.controller;

import com.ramannada.springdemo.entity.Mahasiswa;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController extends BaseController {
    @GetMapping("/")
    public Map<String, Object> index() {
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNama("labib muhajir");
        mahasiswa.setNim("20171201");
        return convertModel(mahasiswa, HttpStatus.OK);
    }
}
