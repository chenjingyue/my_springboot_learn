package com.alarm.spring.boot.autoconfigure.service;

import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;

public interface AlarmWarnService {

    /**
     * 发送信息
     *
     * @param notifyMessage message
     */
    void send(NotifyMessage notifyMessage);
}
