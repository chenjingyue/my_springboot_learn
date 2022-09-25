package com.alarm.spring.boot.autoconfigure.aop;

import com.alarm.spring.boot.autoconfigure.template.AlarmFactoryExecute;
import com.alarm.spring.boot.autoconfigure.template.AlarmTemplateProvider;
import com.alarm.spring.boot.autoconfigure.annotation.Alarm;
import com.alarm.spring.boot.autoconfigure.entity.AlarmInfo;
import com.alarm.spring.boot.autoconfigure.entity.AlarmTemplate;
import com.alarm.spring.boot.autoconfigure.entity.NotifyMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;


@Aspect
public class AlarmAspect {

    private AlarmTemplateProvider alarmTemplateProvider;


    public AlarmAspect(AlarmTemplateProvider alarmTemplateProvider) {
        this.alarmTemplateProvider = alarmTemplateProvider;
    }

    @Pointcut(value = "@annotation(alarm)")
    public void alarmPointcut(Alarm alarm) {
    }


    @Around(value = "alarmPointcut(alarm)", argNames = "joinPoint,alarm")
    public Object around(ProceedingJoinPoint joinPoint, Alarm alarm) throws Throwable {
        Object result = joinPoint.proceed();
        handleAlarm(alarm);
        return result;

    }

    private void handleAlarm(Alarm alarm) {
        if (null == this.alarmTemplateProvider) {
            return;
        }
        boolean successNotice = alarm.successNotice();
        if (successNotice) {

            // TODO: 2022/9/20  处理正常的通知内容
        }
    }

    @AfterThrowing(pointcut = "alarmPointcut(alarm)", argNames = "alarm,e", throwing = "e")
    public void doAfterThrow(Alarm alarm, Exception e) {
        if (null == alarmTemplateProvider) {
            return;
        }

        // 生成模板内容
        String templateId = alarm.templateId();
        AlarmTemplate alarmTemplate = alarmTemplateProvider.loadingAlarmTemplate(templateId);
        String content = alarmTemplate.getContent();
//        content = "标题：#{title}\n状态：#{state}\n异常信息：#{exceptionInfo}";
        String exceptionInfo = exceptionStackTrace(e);
        AlarmInfo info = new AlarmInfo(alarm.title(), "失败", "#FF4B2B", exceptionInfo);
        String parsedContent = parseContent(content, info);


//        Map<String,Object> alarmParamMap = new HashMap<>();
//        alarmParamMap.put("title", alarm.title());
//        alarmParamMap.put("state", "失败");
//        alarmParamMap.put("stateColor","#FF4B2B");
//        alarmParamMap.put("exception", e.getMessage());

        // 发送警告
        NotifyMessage message = new NotifyMessage();
        message.setTitle(alarm.title());
        message.setMessage(parsedContent);

        AlarmFactoryExecute.execute(message);

    }

    public String parseContent(String content, AlarmInfo info) {
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext parserContext = new TemplateParserContext();
        Expression expression = parser.parseExpression(content, parserContext);
        String value = expression.getValue(info, String.class);
        return value;
    }

    public String exceptionStackTrace(Exception e) {
        String s = e.fillInStackTrace().toString();
        StackTraceElement[] stackTrace = e.getStackTrace();
//        List<StackTraceElement> collect = Arrays.stream(stackTrace).collect(Collectors.toList());
        String collect = Arrays.stream(stackTrace).map(Objects::toString).collect(Collectors.joining("\n\t"));
//        String collect1 = collect.stream().map(Objects::toString).collect(Collectors.joining("\n\t"));
        return s + "\n\t" + collect;
    }

}
