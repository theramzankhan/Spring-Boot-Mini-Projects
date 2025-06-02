package com.example.BootBatchEx3.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationImp implements JobExecutionListener {
	
	private Logger logger = LoggerFactory.getLogger(JobCompletionNotificationImp.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Job Started");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("Job Completed");
	}

}
