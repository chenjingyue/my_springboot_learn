package com.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_group")
public class TestConsumer implements RocketMQListener<String> {
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}
