package com.demo.batch.demo1;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Service;

@Service
public class ItemReaderService2 implements ItemReader<String> {
    private int count = 0;

    public String read() throws Exception {
        if (ItemProcessorService.message != null && count < ItemProcessorService.message.length) {
            return ItemProcessorService.message[count++];
        }
        count = 0;
        return null;
    }
}
