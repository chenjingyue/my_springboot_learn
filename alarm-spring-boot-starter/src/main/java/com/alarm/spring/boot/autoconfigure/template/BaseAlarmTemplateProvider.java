package com.alarm.spring.boot.autoconfigure.template;

import com.alarm.spring.boot.autoconfigure.entity.AlarmTemplate;
import org.springframework.util.StringUtils;

public abstract class BaseAlarmTemplateProvider implements AlarmTemplateProvider {

    @Override
    public AlarmTemplate loadingAlarmTemplate(String templateId) {
        if (!StringUtils.hasText(templateId)) {
            throw new RuntimeException("告警模板配置id不能为空");
        }
        return getAlarmTemplate(templateId);
    }

    abstract AlarmTemplate getAlarmTemplate(String templateId);
}
