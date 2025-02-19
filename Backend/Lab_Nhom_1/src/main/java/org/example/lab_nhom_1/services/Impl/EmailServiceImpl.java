package org.example.lab_nhom_1.services.Impl;

import org.example.lab_nhom_1.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService
{
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void sendOtpEmail(String to, String otp)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP code");
        message.setText("Your OTP code is " + otp);
        mailSender.send(message);
    }
}
