package com.alarm.spring.boot.autoconfigure.service;

import com.alarm.spring.boot.autoconfigure.config.MailConfig;
import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;

public abstract class MailExecute {


    protected MailConfig mailConfig;

    public MailConfig getMailConfig() {
        return mailConfig;
    }

    public void setMailConfig(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    public abstract void sendMail(NotifyMessage notifyMessage);
}
