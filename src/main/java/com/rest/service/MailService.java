package com.rest.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
    public boolean sendEmail(String to, String message, String subject) {
        boolean flag = false;
        String from = "learn.mike.helloworld@gmail.com";
        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            //step1: get the session object
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("learn.mike.helloworld@gmail.com", "tugoutumkwlgxrpl");
                }
            });
            session.setDebug(true);

            //step2: compose the message
            MimeMessage mimeMessage = new MimeMessage(session);
            try {
                mimeMessage.setFrom(from);
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);

                //step3: send the message
                Transport.send(mimeMessage);
                System.out.println("--------\nmessage sent successfully!\n--------");
            } catch (Exception e) {
                e.printStackTrace();
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}