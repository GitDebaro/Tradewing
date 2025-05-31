package com.tradewing.services;

import com.tradewing.models.UserEntity;

public interface JWTUtils {

	String createToken(String email);

    UserEntity decode(String token);
}