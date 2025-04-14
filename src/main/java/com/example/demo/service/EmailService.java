package com.example.demo.service;

import com.example.demo.Exception.EmailFailureException;
import com.example.demo.Model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${app.frontend.url}")
    private String frontendUrl;



    @Value("${email.from}")
    private String fromAddress;

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
       return message;
    }

    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();

        message.setTo( verificationToken.getUser().getEmail());
        message.setSubject("Verification Email");
        message.setText("This is verification email.\n"+frontendUrl+ "auth/verify?token=" + verificationToken.getToken());
        try{
            mailSender.send(message);
        }catch (MailException ex){
            ex.printStackTrace();
            throw new EmailFailureException();
        }
    }

}
