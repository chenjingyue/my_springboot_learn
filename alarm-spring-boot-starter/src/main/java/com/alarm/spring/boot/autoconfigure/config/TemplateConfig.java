package com.alarm.spring.boot.autoconfigure.config;

import com.alarm.spring.boot.autoconfigure.entity.AlarmTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

//@Component
@ConfigurationProperties(value = "spring.alarm.template")
public class TemplateConfig {

    public static final String PREFIX = "spring.alarm.template";

    private boolean enabled;
    private String source;
    private Map<String, AlarmTemplate> templates;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, AlarmTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, AlarmTemplate> templates) {
        this.templates = templates;
    }
}
