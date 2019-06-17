package com.ppt.pptsso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 一个发邮件的类
 * */

@Service
public class MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;


    public void send(String subject,String text,String from,String to) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setSubject(subject);
        helper.setText(text,true);
        helper.setFrom(from);
        helper.setTo(to);

        mailSender.send(mimeMessage);
    }
}
