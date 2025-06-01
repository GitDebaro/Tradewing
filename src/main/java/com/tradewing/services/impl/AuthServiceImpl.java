package com.tradewing.services.impl;

import com.tradewing.services.AuthService;
import com.tradewing.services.JWTUtils;
import com.tradewing.models.UserEntity;
import com.tradewing.dto.TokenCredential;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.tradewing.repos.UserRepo;
import lombok.RequiredArgsConstructor;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepo usrRepo;
    
    private final JWTUtils jwt;

    @Value("${google.client.id}")
    private String CLIENT_ID;

    @Override
    public ResponseEntity<?> googleLogin(TokenCredential googleToken) {
        System.out.println("[G-OAuth] Inbound request");
        try {
            GoogleIdToken idToken = verifyGoogleToken(googleToken.getToken());
            if (idToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            UserEntity user = findOrCreateUser(idToken.getPayload());
            String jwtToken = jwt.createToken(user.getEmail());

            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public GoogleIdToken verifyGoogleToken(String token) throws Exception {
        var transport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

        return verifier.verify(token);
    }

    public UserEntity findOrCreateUser(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();

        return usrRepo.findByEmail(email).orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setEmail(email);
            newUser.setPassword("GOOGLE-USER");
            newUser.setName((String) payload.get("given_name"));
            newUser.setSurname((String) payload.get("family_name"));
            newUser.setImage((String) payload.get("picture"));
            return usrRepo.save(newUser);
        });
    }
}