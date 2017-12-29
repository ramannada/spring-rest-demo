package com.ramannada.springdemo.controller;

import com.ramannada.springdemo.entity.Image;
import com.ramannada.springdemo.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ImageController extends BaseController {
    @Autowired
    ImageService imageService;

    @PostMapping("/image/upload")
    public ResponseEntity<?> upload(MultipartHttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Image> image = imageService.save(request);

        if (image.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().body(image);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Image image = imageService.get(id);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(image);
    }

    @GetMapping("image/file/{id}")
    public ResponseEntity<byte[]> getFile(HttpServletResponse response, @PathVariable("id") Long id) {
        InputStream inputStream = imageService.getFile(id);

        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] media = new byte[0];

        try {
            media = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (media == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity.ok().headers(headers).body(media);

//        InputStream inputStream = imageService.getFile(id);
//        try {
//            IOUtils.copy(inputStream, response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        imageService.delete(id);

//        if (imageService.isExist(id)) {
//            return ResponseEntity.unprocessableEntity().build();
//        }

        return ResponseEntity.ok().build();
    }
}
