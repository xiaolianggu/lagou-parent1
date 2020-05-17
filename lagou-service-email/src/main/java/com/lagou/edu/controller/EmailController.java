package com.lagou.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lagou.edu.service.EmailService;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/email")
public class EmailController {
    //@Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailService emailService;
    @GetMapping("/sendCodeToEmail/{email}/{code}")
    public Boolean sendCodeToEmail(@PathVariable("email") String email , @PathVariable("code") String code) {
       /* MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("3345@qq.com");
            helper.setTo(email);
            helper.setSubject("验证码");
            helper.setText(code, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);*/
        return emailService.sendCodeToEmail(email, code);
    }


}
