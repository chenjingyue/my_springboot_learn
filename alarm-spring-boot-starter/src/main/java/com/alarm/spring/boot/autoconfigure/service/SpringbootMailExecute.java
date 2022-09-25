package com.alarm.spring.boot.autoconfigure.service;

import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class SpringbootMailExecute extends MailExecute implements InitializingBean {

    private JavaMailSenderImpl javaMailSender;

    public JavaMailSenderImpl getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(NotifyMessage notifyMessage) {
        System.out.println("--------------SpringbootMailExecute------------");
        MimeMessage message = javaMailSender.createMimeMessage();
//        message.setRecipients();
//        SimpleMailMessage message1 = new SimpleMailMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(notifyMessage.getTitle());
            helper.setText(notifyMessage.getMessage(), false);
            helper.setFrom(mailConfig.getFrom());//发送者的邮箱地址
            helper.setTo(mailConfig.getTo()); //接收者的邮箱地址
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == javaMailSender){
            throw new RuntimeException("未配置邮件属性spring.mail");
        }
    }
}
