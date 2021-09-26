package com.rmslab.upohar.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class) ;

    private final JavaMailSender javaMailSender ;

    @Override
    @Async
    public void send(String toAddress, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage() ;
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8") ;
            mimeMessageHelper.setText(email,true);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject("Upohar Registration: Confirm your email");
            mimeMessageHelper.setFrom("upohar_admin@gmail.com");
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException) {
            LOGGER.error("failed to send email ", messagingException);
            throw new IllegalStateException("failed to send email") ;
        }
    }
}
