package com.tradewing.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tradewing.models.UserEntity;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepo usrRepo;

	@Override
	public List<UserEntity> getAllUsers(){
		return usrRepo.findAll();
	}

	@Override
	public ResponseEntity<?> addUser(UserEntity user){
		System.out.println("[REGISTER] New request inbound: " + user.getEmail());
		String hashedPass = DigestUtils.sha256Hex(user.getPassword());
		user.setPassword(hashedPass);
		try{
			UserEntity newUser = usrRepo.save(user);
				System.out.println("[REGISTER][SUCCESS] User created");
				return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
		}
		catch(Exception e){
				System.out.println("[REGISTER][ERROR] Failed to create a new User");
				if(e.getMessage().contains("duplicate key value")){
					return new ResponseEntity<>("There is already an account linked to this email", HttpStatus.CONFLICT);
				}
				return new ResponseEntity<>("An error ocurred while creating a new User. Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}