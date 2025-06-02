package com.example.BatchJobAtRuntime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.BatchJobAtRuntime.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/run")
	public String runJob(@RequestParam("jobName") String jobName,
						 @RequestParam("file") MultipartFile file) throws Exception {
		return jobService.runJob(jobName, file);
	}

}
