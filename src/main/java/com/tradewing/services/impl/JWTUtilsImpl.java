package com.tradewing.services.impl;

import com.tradewing.models.UserEntity;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.JWTUtils;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Value;


import javax.crypto.SecretKey;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JWTUtilsImpl implements JWTUtils {

    @Value("${my.secret.jwt}")
    private String jwtSecret;

	private final UserRepo usrp;

    public UserEntity decode(String token){
        UserEntity user;
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(getSigningKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
		String email = claims.getIssuer();
		Date expiration = claims.getExpiration();
		Date now = new Date();
		if(expiration.before(now)){
			throw new RuntimeException("[JWTUTILS SERVICE]: Token is expired");
		}
		try{
			user = usrp.findUserByEmail(email);
		}catch(Exception e){
			throw new RuntimeException("[JWTUTILS SERVICE]: User not found");
		}

        return user;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

    @Override
    public String createToken(String email) {
        Date now = new Date();
        Date expiring = new Date(now.getTime() + 86400000);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String token = Jwts.builder().setIssuer(email)
                                .setIssuedAt(now)
                                .setExpiration(expiring)
                                .signWith(key,SignatureAlgorithm.HS256)
                                .compact();
        System.out.println("[JWTUTILS SERVICE]: Token created: " + token);
        return token;
    }
    
    
}