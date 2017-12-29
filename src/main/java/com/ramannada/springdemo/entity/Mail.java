package com.ramannada.springdemo.entity;

import org.springframework.util.MimeType;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class Mail extends BaseEntity {
    private String from;
    private String subject;
    private String body;
    List<InputStream> attachments;
    List<String> attachmentNames;
    List<String> mimeTypes;
    Collection<String> recipients;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<InputStream> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<InputStream> attachments) {
        this.attachments = attachments;
    }

    public List<String> getMimeTypes() {
        return mimeTypes;
    }

    public void setMimeTypes(List<String> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    public Collection<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(Collection<String> recipients) {
        this.recipients = recipients;
    }

    public List<String> getAttachmentNames() {
        return attachmentNames;
    }

    public void setAttachmentNames(List<String> attachmentNames) {
        this.attachmentNames = attachmentNames;
    }
}
