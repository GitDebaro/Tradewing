package com.tradewing.services.impl;

import com.tradewing.models.UserEntity;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
	public void addUser(UserEntity user){
		usrRepo.save(user);
	}

}