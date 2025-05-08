package com.tradewing.services;

import org.springframework.http.ResponseEntity;

import com.tradewing.models.UserEntity;
import java.util.List;

public interface UserService {

	List<UserEntity> getAllUsers();

	ResponseEntity<?> addUser(UserEntity user);

	String authenticate(String email, String password);

    String generateToken(String email);
	
}