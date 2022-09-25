package com.alarm.spring.boot.autoconfigure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(MailConfig.PREFIX)
public class MailConfig {
    public static final String PREFIX = "spring.alarm.mail";
    private String to;
    private String from;

    private boolean useSpringbootMail;
    private boolean enabled;

    private String username;
    private String password;
    private String host;
    private String port;

    public boolean isUseSpringbootMail() {
        return useSpringbootMail;
    }

    public void setUseSpringbootMail(boolean useSpringbootMail) {
        this.useSpringbootMail = useSpringbootMail;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
