package com.alarm.spring.boot.autoconfigure.template;

import com.alarm.spring.boot.autoconfigure.entity.AlarmTemplate;
import com.alarm.spring.boot.autoconfigure.config.TemplateConfig;
import org.springframework.util.ObjectUtils;

import java.util.Map;

//@Service
public class YamlAlarmTemplateProvider extends BaseAlarmTemplateProvider{

    private TemplateConfig templateConfig;

    public YamlAlarmTemplateProvider(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    @Override
    public AlarmTemplate getAlarmTemplate(String templateId) {
        Map<String, AlarmTemplate> templates = templateConfig.getTemplates();
        AlarmTemplate alarmTemplate = templates.get(templateId);
        if (ObjectUtils.isEmpty(alarmTemplate)) {
            throw new RuntimeException("模板未配置");
        }
        return alarmTemplate;
    }
}
