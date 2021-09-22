package com.example.springbatch.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.springbatch.dto.UserDto;
import com.example.springbatch.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserItemprocessor implements ItemProcessor<UserDto, User> {

	@Override
	public User process(UserDto item) throws Exception {
		log.info("Step 2 Processing the file");
		User user = new User();
		user.setFirstName(item.getFirstName().toUpperCase());
		user.setLastName(item.getLastName().toUpperCase());
		user.setAge(item.getAge());
		return user;
	}

}
