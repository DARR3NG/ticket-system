package org.elkastali.usericroservice.service;


import org.elkastali.usericroservice.entities.User;

public interface EmailService {
     void sendMail(User user, String message);
    void sendSimpleMailMessage(User user, String token);
    void sendMimeEmailWithAttachment(String name, String to, String token);
    void sendMimeEmailWithEmbeddedImages(String name, String to, String token);
    void sendMimeEmailWithEmbeddedFiles(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendHtmlEmailWithEmbeddeFiles(String name, String to, String token);

}
