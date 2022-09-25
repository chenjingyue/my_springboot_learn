package com.alarm.spring.boot.autoconfigure.template;

import com.alarm.spring.boot.autoconfigure.entity.AlarmTemplate;

public interface AlarmTemplateProvider {

    /**
     * 加载告警模板
     *
     * @param templateId 模板id
     * @return
     */
    AlarmTemplate loadingAlarmTemplate(String templateId);
}
