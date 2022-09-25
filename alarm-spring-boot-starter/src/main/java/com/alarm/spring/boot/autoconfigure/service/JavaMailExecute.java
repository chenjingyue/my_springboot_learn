package com.alarm.spring.boot.autoconfigure.service;

import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailExecute extends MailExecute{


    @Override
    public void sendMail(NotifyMessage notifyMessage) {
        System.out.println("--------------JavaMailExecute------------");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", this.mailConfig.getHost());
        properties.setProperty("mail.smtp.port", mailConfig.getPort());
        Session session = Session.getInstance(properties);
        session.setDebug(false);
        MimeMessage message = new MimeMessage(session);
        Transport transport = null;
        try {
            message.setSubject(notifyMessage.getTitle());
            message.setContent(notifyMessage.getMessage(), "text/plain;charset=UTF-8");
            message.setRecipients(Message.RecipientType.TO, mailConfig.getTo());
            message.setFrom(mailConfig.getFrom());
            transport = session.getTransport();
            transport.connect(mailConfig.getUsername(), mailConfig.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != transport) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
