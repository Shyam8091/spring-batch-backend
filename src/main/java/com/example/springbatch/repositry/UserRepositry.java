package com.example.springbatch.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbatch.entity.User;

@Repository
public interface UserRepositry extends JpaRepository<User, Integer> {

}
