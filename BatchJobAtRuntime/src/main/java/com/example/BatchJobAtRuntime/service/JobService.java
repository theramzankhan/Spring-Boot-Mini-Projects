package com.example.BatchJobAtRuntime.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.BatchJobAtRuntime.factory.JobFactory;

@Service
public class JobService {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobFactory jobFactory;
	
	public String runJob(String jobName, MultipartFile file) throws Exception {
		Job job = jobFactory.createJob(jobName, file);
	
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("time", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters);
		return "Job" + jobName + " started!";
	
	}

}
