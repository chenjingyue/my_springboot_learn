package com.demo.producer.controller;

import com.demo.producer.service.IProducerService;
import com.demo.producer.service.ProducerServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    IProducerService producerService;

    @RequestMapping("/producer")
    public String producerTransaction(@RequestParam String id) {
        producerService.producerTransaction(id);

//        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        return "hello";
    }
}
