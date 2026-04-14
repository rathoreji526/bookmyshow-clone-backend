package com.bookmyshow.bmscore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

//    @EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
    public void send(){
        this.sendEmail("mr.rathoreji52@gmail.com" , "Application started" , "Hello mr.rathoreji52@gmail.com\nYour application is started now.");
        log.info("Email successfully sent.");
    }
}