package com.demo.batch.db2db.controller;

import com.demo.batch.db2db.config.DataSourceHolder;
import com.demo.batch.db2db.entity.User;
import com.demo.batch.db2db.mapper.slave.UserToDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
public class BatchController {


    private static final Logger log = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private UserService userService;


    @GetMapping("/batch/job/{jobName}")
    public String launchJob(@PathVariable("jobName") String jobName) {
        Collection<String> jobNames = jobRegistry.getJobNames();
        boolean contains = jobNames.contains(jobName);
        if (!contains) {
            return "no find job!!!";
        }
        try {
            Job job = jobRegistry.getJob(jobName);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("data",new Date())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("fail!!! ", e);
            return "fail!!!";
        }
        return "success!!!";
    }


    @GetMapping("/test")
    public String test(){

        DataSourceHolder.set("slave");
        userService.insertUser();
        return "aaa";
    }
}
