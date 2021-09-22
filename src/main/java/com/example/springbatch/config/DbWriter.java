package com.example.springbatch.config;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springbatch.entity.User;
import com.example.springbatch.repositry.UserRepositry;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DbWriter implements ItemWriter<User> {

	@Autowired
	private UserRepositry userRepositry;

	@Override
	public void write(List<? extends User> users) throws Exception {
		log.info("Writing records into Database");
		userRepositry.saveAll(users);
	}

}
