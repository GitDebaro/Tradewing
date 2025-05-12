package com.tradewing.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tradewing.models.UserEntity;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.UserService;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;


import javax.crypto.SecretKey;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepo usrRepo;

	@Value("${my.secret.jwt}")
    private String jwtSecret;

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

	public String authenticate(String email, String rawPassword){
        Optional<UserEntity> Optionaluser = usrRepo.findByEmail(email);

        if(Optionaluser.isEmpty()){
            throw new RuntimeException("[LOGIN] User" + email + "not found");
        }

        UserEntity user = Optionaluser.get();

        if(!DigestUtils.sha256Hex(rawPassword).equals(user.getPassword())){
            throw new RuntimeException("[LOGIN]: Wrong password, User: " + user.getEmail());
        }

        System.out.println("[LOGIN]: Login exitoso, generando token.");
        return generateToken(user.getEmail());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiring = new Date(now.getTime() + 86400000);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String token = Jwts.builder().setIssuer(email)
                                .setIssuedAt(now)
                                .setExpiration(expiring)
                                .signWith(key,SignatureAlgorithm.HS256)
                                .compact();
        System.out.println("[LOGIN]: Token generado: " + token);
        return token;
    }

}