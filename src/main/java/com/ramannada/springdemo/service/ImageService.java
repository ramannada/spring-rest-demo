package com.ramannada.springdemo.service;

import com.ramannada.springdemo.entity.Image;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface ImageService extends BaseService<Image> {
    List<Image> save(MultipartHttpServletRequest request) throws SQLException;

    InputStream getFile(Long id);
}
