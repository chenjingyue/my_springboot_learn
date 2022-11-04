package com.demo.batch.db2db.batch.reader;

import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatchItemReader<T> extends MyBatisPagingItemReader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatchItemReader.class);

    @Override
    protected void doReadPage() {
//        LOGGER.info(Thread.currentThread().getName() + "--doReadPage");
        super.doReadPage();
        int currentItemCount = this.getCurrentItemCount();
//        LOGGER.info(Thread.currentThread().getName() + "--currentItemCount" + currentItemCount);
    }
}
