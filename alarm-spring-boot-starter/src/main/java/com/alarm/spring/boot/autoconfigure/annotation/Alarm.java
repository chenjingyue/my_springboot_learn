package com.alarm.spring.boot.autoconfigure.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Alarm {

    // 标题
    String title() default "";

    // 模板id
    String templateId() default "";

    // 成功是否通知
    boolean successNotice() default false;


}
