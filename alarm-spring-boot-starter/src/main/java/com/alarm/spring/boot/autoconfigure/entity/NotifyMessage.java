package com.alarm.spring.boot.autoconfigure.entity;

public class NotifyMessage {

    private String title;
    private String message;
    private String messageType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "NotifyMessage{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
