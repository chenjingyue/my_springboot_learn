package com.cy.springbootlearndemo.base.quartz;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduledJob {

    String name();

    String group() default "DEFAULT_GROUP";

    String cronExp();
}
