package com.alarm.spring.boot.autoconfigure.service;

import com.alarm.spring.boot.autoconfigure.config.MailConfig;
import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailWarnService implements AlarmWarnService, InitializingBean {

    private MailExecute mailExecute;


    @Override
    public void send(NotifyMessage notifyMessage) {
        mailExecute.sendMail(notifyMessage);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public MailExecute getMailExecute() {
        return mailExecute;
    }

    public void setMailExecute(MailExecute mailExecute) {
        this.mailExecute = mailExecute;
    }


    public void init(MailConfig mailConfig, JavaMailSenderImpl javaMailSender) {
        boolean useSpringbootMail = mailConfig.isUseSpringbootMail();
        if (useSpringbootMail) {
            SpringbootMailExecute springbootMailExecute = new SpringbootMailExecute();
            springbootMailExecute.setJavaMailSender(javaMailSender);
            springbootMailExecute.setMailConfig(mailConfig);
            mailExecute = springbootMailExecute;
        } else {
            JavaMailExecute javaMailExecute = new JavaMailExecute();
            javaMailExecute.setMailConfig(mailConfig);
            mailExecute = javaMailExecute;
        }
    }
}
