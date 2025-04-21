package com.tradewing.services;

import com.tradewing.models.UserEntity;
import java.util.List;

public interface UserService {

	List<UserEntity> getAllUsers();

	void addUser(UserEntity user);

}