package com.ramannada.springdemo.controller;

import com.ramannada.springdemo.service.impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class MailController extends BaseController {
    @Autowired
    MailService mailService;

    @PostMapping("mail")
    public ResponseEntity<?> send(MultipartHttpServletRequest request) {
        try {
            mailService.send(request);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }
}
