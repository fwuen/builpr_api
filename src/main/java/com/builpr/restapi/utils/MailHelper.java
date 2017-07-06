package com.builpr.restapi.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static com.builpr.Constants.*;

/**
 * @author Marco Geißler
 *
 * class that provides methods for setting up a mail client
 */
public class MailHelper {

    private static Session getSession() {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.smtp.port", TLS_PORT);

        /*
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.quitwait", "false");
        properties.put("mail.smtp.auth", "true");*/

        return Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(CONFIRMATION_TOKEN_SENDER_ADDRESS, CONFIRMATION_TOKEN_SENDER_PASSWD);
                    }
                });
    }

    public static void send(String toAddress, String subject, String text) throws MessagingException {

        Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(CONFIRMATION_TOKEN_SENDER_ADDRESS));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
    }
}
