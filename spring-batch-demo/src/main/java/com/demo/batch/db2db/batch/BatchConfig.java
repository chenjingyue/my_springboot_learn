package com.demo.batch.db2db.batch;

import com.demo.batch.db2db.batch.processor.UserItemProcessor;
import com.demo.batch.db2db.batch.reader.MyBatchItemReader;
import com.demo.batch.db2db.batch.reader.MyBatchPageItemReader;
import com.demo.batch.db2db.batch.writer.MyBatchItemWriter;
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
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

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
//                .reader(pageReader())
                .reader(pageItemReader())
                .processor(itemProcessor())
                .writer(myItemWriter())
//                .taskExecutor(myTaskExecutor())
//                .allowStartIfComplete(false)
//                .startLimit(1)
//                .throttleLimit(5)
                .build();
    }

    @Bean
    public TaskExecutor myTaskExecutor() {

        int corePoolSize = 4;
        int maxPoolSize = 10;
        int keepAliveSeconds = 100;
        int queueCapacity = 20;

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);

        return threadPoolTaskExecutor;
    }

    @Bean
    public UserItemProcessor itemProcessor() {
        UserItemProcessor userItemProcessor = new UserItemProcessor();
        return userItemProcessor;
    }

    public void test(){
        stepBuilderFactory.get("test")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        StepExecution stepExecution = contribution.getStepExecution();
                        return null;
                    }
                })
                .build();
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
                .pageSize(10)
                .queryId("com.demo.batch.db2db.mapper.master.UserDao.selectAllUser")
                .sqlSessionFactory(sqlSessionFactory)
                .maxItemCount(200)
                .build();
        return reader;
    }

    public  MyBatisPagingItemReader<User> reader() {
        MyBatchItemReader<User> userMyBatchItemReader = new MyBatchItemReader<>();
//        userMyBatchItemReader.setMaxItemCount(200);
        userMyBatchItemReader.setQueryId("com.demo.batch.db2db.mapper.master.UserDao.selectAllUser");
        userMyBatchItemReader.setSqlSessionFactory(sqlSessionFactory);
        userMyBatchItemReader.setPageSize(500);
        return userMyBatchItemReader;
    }

    public MyBatchPageItemReader<User> pageItemReader() {
        MyBatchPageItemReader<User> itemReader = new MyBatchPageItemReader<>();
//        itemReader.setMaxItemCount(200);
        itemReader.setQueryId("com.demo.batch.db2db.mapper.master.UserDao.selectAllUserLimitId");
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        itemReader.setPageSize(500);
        return itemReader;
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
