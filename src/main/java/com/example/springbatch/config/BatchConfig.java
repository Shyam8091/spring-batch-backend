package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.springbatch.dto.UserDto;
import com.example.springbatch.entity.User;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	

	

	/**
	 * Step 1 reading the file
	 * 
	 * @return reader
	 */
	@Bean
	@StepScope
	public FlatFileItemReader<UserDto> flatFileItemReader() {

		FlatFileItemReader<UserDto> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("UserInfo.csv"));
		log.info("Starting point Reading the file from Resource");
		reader.setName("CSV-READER");
		reader.setLineMapper(getLineMapper());
		reader.setLinesToSkip(1);
		return reader;
	}

	/**
	 * To map data to POJO class
	 * 
	 * @return lineMapper
	 */
	private LineMapper<UserDto> getLineMapper() {
		DefaultLineMapper<UserDto> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setNames(new String[] { "FirstName", "LastName", "Age" });
		delimitedLineTokenizer.setIncludedFields(new int[] { 0, 2, 3 });
		BeanWrapperFieldSetMapper<UserDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(UserDto.class);
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return defaultLineMapper;
	}

	/**
	 * Step 2 process the file and do required changes
	 * 
	 * @return itemProcessor
	 */
	@Bean
	public UserItemprocessor itemprocessor() {
		return new UserItemprocessor();
	}

	/**
	 * Writes the records in Database
	 * 
	 * @return writer
	 */
	@Bean
	public DbWriter writer() {
		return new DbWriter();
	}

	@Bean
	public Job importJob() {
		return this.jobBuilderFactory.get("USER-IMPORT-JOB").incrementer(new RunIdIncrementer())
				.flow(readProcessWrite()).end().build();

	}

	@Bean
	public Step readProcessWrite() {

		return this.stepBuilderFactory.get("READ-PROCESS-WRITE").<UserDto, User>chunk(100).reader(flatFileItemReader())
				.processor(itemprocessor()).writer(writer()).build();
	}
	
	
}
