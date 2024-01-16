package com.elkastali.ticketservice.service.impl;

import com.elkastali.ticketservice.entities.User;
import com.elkastali.ticketservice.service.EmailService;
import com.elkastali.ticketservice.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceIml implements EmailService {

    public static final String NEW_ACCOUNT_VERIFICATION = "New Account Verification";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;
    @Override
    @Async
    public void sendSimpleMailMessage(User user, String token) {

        try{

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(user.getEmail());
            message.setText(EmailUtils.getVerifyMessage(user,host,token));
            javaMailSender.send(message);     // Send email

            


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void sendMail(User user,String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Ticket Service");
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendMimeEmailWithAttachment(String name, String to, String token) {

    }

    @Override
    public void sendMimeEmailWithEmbeddedImages(String name, String to, String token) {

    }

    @Override
    public void sendMimeEmailWithEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    public void sendHtmlEmail(String name, String to, String token) {

    }

    @Override
    public void sendHtmlEmailWithEmbeddeFiles(String name, String to, String token) {

    }
}
