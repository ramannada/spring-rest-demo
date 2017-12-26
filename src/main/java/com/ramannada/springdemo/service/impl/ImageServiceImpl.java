package com.ramannada.springdemo.service.impl;

import com.ramannada.springdemo.dao.ImageDAO;
import com.ramannada.springdemo.entity.Image;
import com.ramannada.springdemo.service.ImageService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService {
    @Autowired
    ServletContext context;
    @Autowired
    private ImageDAO imageDAO;
    @Value("${file.upload.directory}")
    private String storage;

    public List<Image> save(MultipartHttpServletRequest request) throws SQLException {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        List<Image> list = new LinkedList<>();
        Image image = new Image();

        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());

            String newFileNameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename()
                    .substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFileName = newFileNameBase + originalFileExtension;
            String storageDirectory = context.getRealPath(storage);
            String contentType = mpf.getContentType();

            try {
                File newFile = new File(storageDirectory + "/" + newFileName);
                mpf.transferTo(newFile);

                BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 320);
                String thumbnailFileName = newFileNameBase + "-thumbnail.png";
                File thumbnailFile = new File(storageDirectory + "/" + thumbnailFileName);
                ImageIO.write(thumbnail, "png", thumbnailFile);

                image.setName(mpf.getOriginalFilename());
                image.setThumbnailFilename(thumbnailFileName);
                image.setNewFilename(newFileName);
                image.setContentType(contentType);
                image.setSize(mpf.getSize());
                image.setThumbnailSize(thumbnailFile.length());
                image.setUrl(storageDirectory + "/" + newFileName);
                image.setThumbnailUrl(storageDirectory + "/" + thumbnailFileName);
                image.setDeleteUrl("/image/delete/" + newFileName);
                image.setDeleteType("DELETE");
                image = save(image);

                list.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public InputStream getFile(Long id) {
        Image image = imageDAO.get(id);
        File imageFile = new File(image.getUrl());

        try {
            return new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Image save(Image entity) throws SQLException {
        return imageDAO.save(entity);
    }

    @Override
    public Image get(Long id) {
        return imageDAO.get(id);
    }

    @Override
    public List<Image> getAll() {
        return null;
    }

    @Override
    public Boolean isExist(Long id) {
        return null;
    }

    @Override
    public List<Image> find(Image entity) {
        return null;
    }

    @Override
    public Image update(Image entity) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Image image = imageDAO.get(id);
        File imageFile = new File(image.getUrl());
        imageFile.delete();

        File thumbnailFile = new File(image.getThumbnailUrl());
        thumbnailFile.delete();

        imageDAO.delete(id);
    }
}
