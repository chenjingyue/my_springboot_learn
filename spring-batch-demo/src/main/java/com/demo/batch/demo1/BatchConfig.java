package com.demo.batch.demo1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
//@Configuration
//@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReaderService itemReaderService;
    @Autowired
    private ItemReaderService2 itemReaderService2;
    @Autowired
    private ItemProcessorService itemProcessorService;
    @Autowired
    private ItemProcessorService2 itemProcessorService2;
    @Autowired
    private ItemWriterService itemWriterService;

    @Bean
    public Job singleStepJob() {
        return jobBuilderFactory.get("singleStepJob")
                .incrementer(new RunIdIncrementer())
                .start(uppercaseStep())
                .build();
    }

    @Bean
    public Job multiBoundStepsJob() {
        return jobBuilderFactory.get("multiBoundStepsJob")
                .incrementer(new RunIdIncrementer())
                .start(uppercaseStep())
                .next(addMessageStep())
                .build();
    }

    @Bean
    public Step uppercaseStep() {
        return stepBuilderFactory.get("uppercaseStep")
                .<String, String>chunk(2)
                .reader(itemReaderService)
                .processor(itemProcessorService)
                .writer(itemWriterService).build();
    }

    @Bean
    public Step addMessageStep() {
        return stepBuilderFactory.get("addMessageStep")
                .<String, String>chunk(3)
                .reader(itemReaderService2)
                .processor(itemProcessorService2)
                .writer(itemWriterService).build();
    }


    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource(){
        DataSourceBuilder<?> root = DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://192.168.106.128:3306/test1").username("root").password("123");
        DataSource dataSource = root.build();
        return dataSource;

    }

}
