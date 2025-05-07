package com.tradewing.services.impl;

import com.tradewing.models.UserEntity;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.SecretKey;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepo usrRepo;

	@Value("${my.secret.jwt}")
    private String jwtSecret;

	public UserServiceImpl(UserRepo usrRepo) {
        this.usrRepo = usrRepo;
    }

	@Override
	public List<UserEntity> getAllUsers(){
		return usrRepo.findAll();
	}

	@Override
	public void addUser(UserEntity user){
		usrRepo.save(user);
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