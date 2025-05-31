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
        try{
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            var jsonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

            GoogleIdToken idToken = verifier.verify(googleToken.getToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                Optional<UserEntity> userOptional = usrRepo.findByEmail(payload.getEmail());
                UserEntity googleUser;

                if (userOptional.isPresent()) {
                    googleUser = userOptional.get();
                } else {
                    googleUser = new UserEntity();
                    googleUser.setId(null);
                    googleUser.setEmail(payload.getEmail());
                    googleUser.setPassword("GOOGLE-USER");
                    googleUser.setName((String) payload.get("given_name"));
                    googleUser.setSurname((String) payload.get("family_name"));
                    googleUser.setImage((String) payload.get("picture"));
                    usrRepo.save(googleUser);
                }

                String trdToken = jwt.createToken(googleUser.getEmail());

                return ResponseEntity.ok(trdToken);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}