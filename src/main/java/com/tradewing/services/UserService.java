package com.tradewing.services;

import org.springframework.http.ResponseEntity;

import com.tradewing.models.UserEntity;
import com.tradewing.models.ProductEntity;
import com.tradewing.dto.UserInfo;
import com.tradewing.dto.TokenCredential;
import com.tradewing.dto.UpdateUserPayload;
import java.util.List;

public interface UserService {

	List<UserEntity> getAllUsers();

	ResponseEntity<?> addUser(UserEntity user);

	ResponseEntity<TokenCredential> authenticate(String email, String password);

	ResponseEntity<UserInfo> getUserData(String token);

	List<ProductEntity> getMyInventory(String token);

	ResponseEntity<UserInfo> updateUserData (UpdateUserPayload payload);
}