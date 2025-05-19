package com.tradewing.services;

import com.tradewing.dto.TokenCredential;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> googleLogin(TokenCredential googleToken);
}