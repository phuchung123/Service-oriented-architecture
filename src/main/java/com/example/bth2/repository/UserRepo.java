package com.example.bth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bth2.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsername(String username);
}