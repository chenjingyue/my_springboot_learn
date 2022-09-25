package com.alarm.spring.boot.autoconfigure.entity;

public class AlarmInfo {
    private String title;
    private String state;
    private String stateColor;
    private String exceptionInfo;

    public AlarmInfo() {

    }

    public AlarmInfo(String title, String state, String stateColor, String exceptionInfo) {
        this.title = title;
        this.state = state;
        this.stateColor = stateColor;
        this.exceptionInfo = exceptionInfo;
    }

    public String getStateColor() {
        return stateColor;
    }

    public void setStateColor(String stateColor) {
        this.stateColor = stateColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
