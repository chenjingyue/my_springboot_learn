package com.demo.producer.service;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProducerServiceImpl implements IProducerService {


    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    public String producerTransaction(String id) {
        // Build a SpringMessage for sending in transaction
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("name", "name");
        Message<Map<String, Object>> msg = MessageBuilder.withPayload(map).build();
        // In sendMessageInTransaction(), the first parameter transaction name ("test")
        // must be same with the @RocketMQTransactionListener's member field 'transName'
        rocketMQTemplate.sendMessageInTransaction("test-topic-1", msg, null);
        return "SUCCESS";
    }
}
