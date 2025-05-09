package com.tradewing.controllers.impl;

import com.tradewing.controllers.UserController;
import com.tradewing.dto.LoginRequest;
import com.tradewing.dto.LoginResponse;
import com.tradewing.dto.UserInfo;
import com.tradewing.models.UserEntity;
import com.tradewing.services.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/users")
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
	public ResponseEntity<?> addUser(@RequestBody UserEntity user){
		return userSC.addUser(user);
	}

	@Override
	@PostMapping("/loginUser")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		try{
			String token = userSC.authenticate(request.getEmail(), request.getPassword());
			LoginResponse login = new LoginResponse(token);

			return ResponseEntity.ok(login);
		}
		catch(RuntimeException e){
			System.out.println("[LOGIN][ERROR] Failed to login user:" + request.getEmail());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	@PostMapping("/data")
	public ResponseEntity<UserInfo> getUserData(@RequestBody LoginResponse userToken){
		UserInfo info = userSC.getUserData(userToken.getToken());
		if(info != null)
			return ResponseEntity.ok(userSC.getUserData(userToken.getToken()));
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}