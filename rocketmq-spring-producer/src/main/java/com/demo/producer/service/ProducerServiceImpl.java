package com.demo.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ProducerServiceImpl implements IProducerService {


    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    public String producerTransaction(String id) {
        String topic = "test-topic-1";
        batchSendMessage(topic);
        return "SUCCESS";
    }

    private void sendMessage(String destination, String id) {
        // Build a SpringMessage for sending in transaction
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("name", "name" + id);
        Message<Map<String, Object>> msg = MessageBuilder.withPayload(map).build();
//        rocketMQTemplate.getProducer().setVipChannelEnabled(false);
        // In sendMessageInTransaction(), the first parameter transaction name ("test")
        // must be same with the @RocketMQTransactionListener's member field 'transName'
//        rocketMQTemplate.sendMessageInTransaction("test-topic-1", msg, null);
        rocketMQTemplate.asyncSend(destination, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("send successful");
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("send fail" + throwable.getMessage());

            }
        });
    }

    public void batchSendMessage(String destination) {
        String[] tags = new String[]{"tag1", "tag2"};
        for (int i = 0; i < 10; i++) {
            String aa = destination +":"+ tags[i % 2];
            sendMessage(aa, String.valueOf(i));
        }
    }
}
