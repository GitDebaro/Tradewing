package com.tradewing.controllers.impl;

import com.tradewing.controllers.UserController;
import com.tradewing.dto.LoginRequest;
import com.tradewing.dto.TokenCredential;
import com.tradewing.dto.UserInfo;
import com.tradewing.dto.UpdateUserPayload;
import com.tradewing.models.UserEntity;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

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
	public ResponseEntity<TokenCredential> login(@RequestBody LoginRequest request) {
		return userSC.authenticate(request.getEmail(), request.getPassword());
	}

	@Override
	@PostMapping("/data")
	public ResponseEntity<UserInfo> getUserData(@RequestBody TokenCredential userToken){
		return userSC.getUserData(userToken.getToken());
	}

	@Override
	@PostMapping("my-inventory")
	public List<ProductEntity> getMyInventory(@RequestBody TokenCredential userToken){
		return userSC.getMyInventory(userToken.getToken());
	}

	@Override
	@PostMapping("/update")
	public ResponseEntity<UserInfo> updateUserData(@RequestBody UpdateUserPayload payload){
		return userSC.updateUserData(payload);
	}
}