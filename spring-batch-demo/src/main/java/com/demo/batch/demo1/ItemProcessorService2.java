package com.demo.batch.demo1;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ItemProcessorService2 implements ItemProcessor<String, String> {
    public String process(String data) throws Exception {
        return data + " dylanz";
    }
}