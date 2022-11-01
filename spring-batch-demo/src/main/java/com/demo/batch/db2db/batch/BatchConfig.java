package com.demo.batch.db2db.batch;

import com.demo.batch.PersonItemProcessor;
import com.demo.batch.db2db.config.DataSourceHolder;
import com.demo.batch.db2db.entity.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.UUID;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job userJob() {
        return jobBuilderFactory.get("userJob")
                .incrementer(new RunIdIncrementer())
                .flow(userStep())
                .end()
                .build();
    }

    @Bean
    public Step userStep() {
        return stepBuilderFactory
                .get("userStep")
                .<User, User>chunk(500)
                .reader(pageReader())
                .processor(itemProcessor())
                .writer(myItemWriter())
                .build();
    }

    @Bean
    public UserItemProcessor itemProcessor() {
        UserItemProcessor userItemProcessor = new UserItemProcessor();
        return userItemProcessor;
    }




    /** ------ ItemReader ------ start---------*/

    public MyBatisCursorItemReader<User> itemReader() {
        MyBatisCursorItemReaderBuilder<User> builder = new MyBatisCursorItemReaderBuilder<>();
        MyBatisCursorItemReader<User> itemReader = builder
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.demo.batch.db2db.mapper.master.UserDao.selectAllUser")
                .maxItemCount(5)
//                .parameterValues()
                .build();

        return itemReader;
    }

    public MyBatisPagingItemReader<User> pageReader() {
        MyBatisPagingItemReaderBuilder<User> readerBuilder = new MyBatisPagingItemReaderBuilder<>();
        MyBatisPagingItemReader<User> reader = readerBuilder
                .pageSize(500)
                .queryId("com.demo.batch.db2db.mapper.master.UserDao.selectAllUser")
                .sqlSessionFactory(sqlSessionFactory)
//                .maxItemCount(20)
                .build();
        return reader;
    }

    /** ------ ItemReader ------ end---------*/




    /** ------ ItemWriter ------ start---------*/
    @Bean
    public ItemWriter<User> myItemWriter(){
        MyBatchItemWriter<User> itemWriter = new MyBatchItemWriter<>();
        itemWriter.setStatementId("com.demo.batch.db2db.mapper.slave.UserToDao.insertUserBatch");
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        return itemWriter;
    }

    public MyBatisBatchItemWriter<User> userMyBatisBatchItemWriter() {
        DataSourceHolder.set("master");
        MyBatisBatchItemWriterBuilder<User> builder = new MyBatisBatchItemWriterBuilder<>();
        MyBatisBatchItemWriter<User> itemWriter = builder.sqlSessionFactory(sqlSessionFactory)
                .statementId("com.demo.batch.db2db.mapper.slave.UserToDao.insertUser")
//                .itemToParameterConverter(new Converter<User, User>() {
//                    @Override
//                    public User convert(User user) {
//                        System.out.println("-------------------->" + user);
//                        user.setName("1" + user.getName());
//                        return user;
//                    }
//                })
                .build();
        return itemWriter;
    }

    public ItemWriter<User> itemWriter() {
        ItemWriter<User> itemWriter = new ItemWriter<User>() {
            @Override
            public void write(List<? extends User> items) throws Exception {
                System.out.println("--------------------------1");
                System.out.println(items);
                System.out.println("--------------------------2");
            }
        };
        return itemWriter;

    }

    /** ------ ItemWriter ------ end--------- */




    /**
     * 注册job到JobRegistry
     * @param jobRegistry
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry){
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }


}
