package com.alarm.spring.boot.autoconfigure.template;

import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;
import com.alarm.spring.boot.autoconfigure.service.AlarmWarnService;

import java.util.ArrayList;
import java.util.List;

public class AlarmFactoryExecute {

    private static List<AlarmWarnService> alarmWarnServiceList = new ArrayList<>();

    public static void addAlarmWarnService(AlarmWarnService alarmWarnService) {
        if (null == alarmWarnServiceList) {
            alarmWarnServiceList = new ArrayList<>();
        }
        alarmWarnServiceList.add(alarmWarnService);
    }

    public static void execute(NotifyMessage notifyMessage){
        for (AlarmWarnService alarmWarnService : alarmWarnServiceList) {
            alarmWarnService.send(notifyMessage);
        }
    }
}
