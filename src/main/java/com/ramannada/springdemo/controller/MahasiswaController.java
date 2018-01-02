package com.ramannada.springdemo.controller;

import com.ramannada.springdemo.entity.Mahasiswa;
import com.ramannada.springdemo.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;
import java.sql.SQLException;
import java.util.*;

@RestController
public class MahasiswaController extends BaseController {
    @Autowired
    MahasiswaService mahasiswaService;

    @PostMapping("/mahasiswa")
    public ResponseEntity<?> create(@RequestBody Mahasiswa mahasiswa, BindingResult result) throws SQLException {
        Mahasiswa response = mahasiswaService.save(mahasiswa);

        if (response != null) {
            URI uri = UriComponentsBuilder.fromPath("user/{id}").buildAndExpand(response.getId()).toUri();

            return ResponseEntity.created(uri).body(response);
        }

        return ResponseEntity.unprocessableEntity().build();
    }


    @GetMapping("/mahasiswa/{id}")
    public ResponseEntity<Mahasiswa> get(@PathVariable("id") Long id) {
        Mahasiswa response = mahasiswaService.get(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mahasiswa")
    public ResponseEntity<?> getAll() {
        List<Mahasiswa> mahasiswa = mahasiswaService.getAll();

        if (mahasiswa.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(mahasiswa);
    }

    @GetMapping("/mahasiswa/page/{page}")
    public ResponseEntity<?> getAllWithPage(@PathVariable("page") int page) {
        int entityPerPage = 5;
        List<Mahasiswa> mahasiswa = mahasiswaService.getAllWithPage(page, entityPerPage);

        if (mahasiswa.size() <= 1) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(mahasiswa);
    }

    @GetMapping("/mahasiswa/find")
    public ResponseEntity<?> find(@RequestParam(value = "nim", required = false) String nim,
                                  @RequestParam(value = "nama", required = false) String nama) {
        Mahasiswa mahasiswa = new Mahasiswa();
        if (nim != null) {
            mahasiswa.setNim(nim);
        }

        if (nama != null) {
            mahasiswa.setNama(nama);
        }

        if (nim == null && nama == null) {
            return ResponseEntity.badRequest().build();
        }



        List<Mahasiswa> response = mahasiswaService.find(mahasiswa);

       if (response.size() == 0) {
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("mahasiswa/update")
    public ResponseEntity<?> update(@RequestBody Mahasiswa mahasiswa) throws SQLException {
        if (!mahasiswaService.isExist(mahasiswa.getId())) {
            return ResponseEntity.notFound().build();
        }

        Mahasiswa response = mahasiswaService.update(mahasiswa);

        if (response == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("mahasiswa/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!mahasiswaService.isExist(id)) {
            return ResponseEntity.notFound().build();
        }

        mahasiswaService.delete(id);

        if (mahasiswaService.isExist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity.ok().build();
    }
}
