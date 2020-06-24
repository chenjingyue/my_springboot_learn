package com.example.my_springboot_learn.base.quartz.jobs;

import com.example.my_springboot_learn.base.quartz.ScheduledJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;


/**
 * 课程七天自动完成 定时任务(每天1:0:0开始)
 */
//@Component
@ScheduledJob(name = "AutoCompleteCourseJob", cronExp = "0 0 1 * * ?")
@DisallowConcurrentExecution
@Slf4j
public class AutoCompleteCourseJob implements Job {


    @Override
    public void execute(JobExecutionContext context) {
        log.info("AutoCompleteCourseJob start...");

        log.info("AutoCompleteCourseJob end...");
    }


}
