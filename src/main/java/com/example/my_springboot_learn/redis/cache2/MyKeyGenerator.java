package com.example.my_springboot_learn.redis.cache2;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MyKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        if (objects.length <= 0)
            return method.getName();
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName()).append("-");
            for (Object object : objects) {
                String value = String.valueOf(object);
                sb.append(value);
            }
            return sb.toString();
        }
    }
}
