package com.tradewing.controllers.impl;

import com.tradewing.controllers.UserController;
import com.tradewing.models.UserEntity;
import com.tradewing.services.UserService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImpl implements UserController {

	private final UserService userSC;

	@Override
	@GetMapping("/")
	public List<UserEntity> getAllUsers(){
		return userSC.getAllUsers();
	}

	@Override
	@PostMapping("/addUser")
	public void addUser(@RequestBody UserEntity user){
		userSC.addUser(user);
	}

}