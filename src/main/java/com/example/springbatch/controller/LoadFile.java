package com.example.springbatch.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbatch.common.RestResponse;
import com.example.springbatch.common.RestUtils;

@RestController
@RequestMapping("/load")
public class LoadFile {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@GetMapping("/loadCsvFile")
	public ResponseEntity<RestResponse<BatchStatus>> loadCsvFile() throws JobParametersInvalidException,
			JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(new Date()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(job, parameters);
		return RestUtils.successResponse(jobExecution.getStatus(), HttpStatus.OK,
				"FILE LOAD STATUS : " + jobExecution.getStatus());
	}
}
