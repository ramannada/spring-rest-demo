package com.ramannada.springdemo.service.impl;


import com.ramannada.springdemo.entity.Mail;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.*;

@Service
public class MailService {
    @Autowired
    JavaMailSender mailSender;

    public void send(MultipartHttpServletRequest request) throws IOException, MessagingException {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        List<InputStream> attachment = new ArrayList<>();
        List<String> attachmentName = new ArrayList<>();
        List<String> mimeType = new ArrayList<>();
        Mail mail = new Mail();
        Tika tika = new Tika();

        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            mimeType.add(tika.detect(mpf.getBytes()));
            attachment.add(mpf.getInputStream());
            attachmentName.add(mpf.getOriginalFilename());
        }

        mail.setRecipients(Arrays.asList(request.getParameter("to").split(",")));
        mail.setSubject(request.getParameter("subject"));
        mail.setBody(request.getParameter("body"));
        mail.setAttachments(attachment);
        mail.setAttachmentNames(attachmentName);
        mail.setMimeTypes(mimeType);
        mail.setFrom(request.getParameter("from"));

        send(mail);

    }

    public void send(Mail mail) throws MessagingException, IOException {
        Objects.requireNonNull(mail.getFrom());
        Objects.requireNonNull(mail.getRecipients());

        javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(mail.getFrom());
        messageHelper.setSubject(mail.getSubject());
        messageHelper.setText(mail.getBody(), true);

        for(String recipients: mail.getRecipients()) {
            messageHelper.addTo(recipients);
        }

        if (mail.getAttachments() != null) {
            for (int i = 0; i < mail.getAttachments().size(); i++) {
                ByteArrayDataSource dataSource =
                        new ByteArrayDataSource(mail.getAttachments().get(i), mail.getMimeTypes().get(i));

                messageHelper.addAttachment(mail.getAttachmentNames().get(i), dataSource);
            }

        }

        mailSender.send(mimeMessage);

    }

}
