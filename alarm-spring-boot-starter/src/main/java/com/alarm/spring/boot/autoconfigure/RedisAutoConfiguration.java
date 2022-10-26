package com.alarm.spring.boot.autoconfigure;

import com.alarm.spring.boot.autoconfigure.redis.RedisMessageListenerMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisAutoConfiguration {


    @Bean
    public RedisMessageListenerMethodProcessor redisMessageListenerMethodProcessor() {
        return new RedisMessageListenerMethodProcessor();
    }


}
