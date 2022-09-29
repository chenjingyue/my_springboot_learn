package com.alarm.spring.boot.autoconfigure.redis;

import com.alarm.spring.boot.autoconfigure.enumeration.ChannelType;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisMessageListener {

    ChannelType mode() default ChannelType.CHANNEL;

    String channel() default "";

}
