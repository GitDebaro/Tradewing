package com.tradewing.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tradewing.models.UserEntity;
import com.tradewing.models.ProductEntity;
import com.tradewing.repos.ProductRepo;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.UserService;
import com.tradewing.services.JWTUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import lombok.RequiredArgsConstructor;
import com.tradewing.dto.UserInfo;
import com.tradewing.dto.UpdateUserPayload;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepo usrRepo;
	private final ProductRepo productSC;
	private final JWTUtils jwt;

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
				usrRepo.save(user);
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

	@Override
	public String authenticate(String email, String rawPassword){
        Optional<UserEntity> Optionaluser = usrRepo.findByEmail(email);

        if(Optionaluser.isEmpty()){
            throw new RuntimeException("[LOGIN] User" + email + "not found");
        }

        UserEntity user = Optionaluser.get();

        if(!DigestUtils.sha256Hex(rawPassword).equals(user.getPassword())){
            throw new RuntimeException("[LOGIN]: Wrong password, User: " + user.getEmail());
        }

        System.out.println("[LOGIN]: Login success");
        return jwt.createToken(user.getEmail());
    }

	@Override
	public UserInfo getUserData(String token){
		try{
			UserEntity currentUser = jwt.decode(token);

			if(currentUser == null)
				return null;

			return UserInfo.builder()
					.name(currentUser.getName())
					.surname(currentUser.getSurname())
					.email(currentUser.getEmail())
					.image(currentUser.getImage())
					.build();
		}
		catch(Exception e){
			System.out.println("[PROFILE][ERROR] Could not get claims from token");
			return null;
		}
	}

	@Override
	public List<ProductEntity> getMyInventory(String token){
		List<ProductEntity> inventory = new ArrayList<>();
		try{
			UserEntity currentUser = jwt.decode(token);

			if(currentUser == null)
				return inventory;

			return productSC.findBySeller(currentUser);
		}
		catch(Exception e){
			System.out.println("[PROFILE][ERROR] Could not get claims from token");
			return inventory;
		}
	}

	@Override
	public ResponseEntity<UserInfo> updateUserData(UpdateUserPayload payload){
		UserInfo response = new UserInfo();
		try{
			UserEntity currentUser = jwt.decode(payload.getToken());

			if(currentUser == null)
				return new ResponseEntity(response,HttpStatus.NOT_FOUND);

			currentUser.setImage(payload.getImage());

			UserEntity updatedUser = usrRepo.save(currentUser);

			response.setName(updatedUser.getName());
			response.setSurname(updatedUser.getSurname());
			response.setEmail(updatedUser.getEmail());
			response.setImage(updatedUser.getImage()); 

			return new ResponseEntity(response,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
		}
	}
}