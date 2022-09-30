package com.alarm.spring.boot.autoconfigure.redis;

import com.alarm.spring.boot.autoconfigure.enumeration.ChannelType;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisMessageListenerMethodProcessor implements SmartInitializingSingleton,
        InitializingBean, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    private RedisMessageListenerContainer container;

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, List<Method>> map = findEligibleMethods();
        addListen(map);
    }

    private Map<String, List<Method>> findEligibleMethods() {
        String[] beanNames = this.beanFactory.getBeanDefinitionNames();
        Map<String, List<Method>> map = new HashMap<>();
        for (String beanName : beanNames) {
            if (!ScopedProxyUtils.isScopedTarget(beanName)) {
                Class<?> type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
                if (null != type) {
                    List<Method> list = new ArrayList<>();
                    // 判断类中方法是否存在@RedisMessageListener注解
                    Method[] methods = type.getMethods();
                    for (Method method : methods) {
                        // todo 添加对方法参数校验
                        RedisMessageListener annotation = method.getAnnotation(RedisMessageListener.class);
                        if (annotation != null) {
                            list.add(method);
                        }
                    }
                    if (!CollectionUtils.isEmpty(list)) {
                        map.put(beanName, list);
                    }
                }
            }
        }
        return map;
    }

    private void addListen(Map<String, List<Method>> map) {
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, List<Method>> stringListEntry : map.entrySet()) {
            String beanName = stringListEntry.getKey();
            List<Method> value = stringListEntry.getValue();
            Object bean = this.beanFactory.getBean(beanName);
            for (Method method : value) {
                String methodName = method.getName();
                MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(bean, methodName);
                listenerAdapter.afterPropertiesSet();
                RedisMessageListener annotation = method.getAnnotation(RedisMessageListener.class);
                Topic topic = getTopic(annotation);
                this.container.addMessageListener(listenerAdapter, topic);
            }
        }
    }

    private static Topic getTopic(RedisMessageListener annotation) {
        ChannelType mode = annotation.mode();
        String channel = annotation.channel();
        Topic topic;
        if (mode == ChannelType.PATTERN) {
            topic = new PatternTopic(channel);
        } else {
            topic = new ChannelTopic(channel);
        }
        return topic;
    }


    @Override
    public void afterPropertiesSet() {
        RedisConnectionFactory redisConnectionFactory = beanFactory.getBean(RedisConnectionFactory.class);
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.afterPropertiesSet();
        container.start();
        this.container = container;
    }


    //    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
    /**
     *  未导入jedis, RedisConnectionFactory 实现类为 LettuceConnectionFactory
     * 原本想法：
     *  在BeanFactoryPostProcessor#postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) 方法中
     *  获取RedisConnectionFactory 的bean，设置到RedisMessageListenerContainer中
     *  再将RedisMessageListenerContainer注册到容器中
     * 报错：
     *  Failed to instantiate [org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration]:
     *  No default constructor found; nested exception is java.lang.NoSuchMethodException:
     *  org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration.<init>()
     * 原因：
     *  通过beanFactory.getBean(RedisConnectionFactory.class) 获取bean时，此时容器还没有到实例化bean步骤，
     *  getBean() 去获取对象，会去创建对象LettuceConnectionFactory，
     *  LettuceConnectionFactory 是通过@Bean 配置在LettuceConnectionConfiguration类中，
     *  需要先去实例化LettuceConnectionConfiguration，此时后置处理器AutowiredAnnotationBeanPostProcessor(推断构造方法)未实例化
     *  只能使用默认无参构造方法，但是LettuceConnectionConfiguration只有一个有参构造方法
     *  导致实例化时报错
     *
     */


}
