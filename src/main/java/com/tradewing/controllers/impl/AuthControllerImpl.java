package com.tradewing.controllers.impl;

import com.tradewing.controllers.AuthController;
import com.tradewing.services.AuthService;
import com.tradewing.dto.TokenCredential;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthControllerImpl implements AuthController{

    private final AuthService authSC;
    
    @PostMapping("/google")
    @Override
    public ResponseEntity<?> googleLogin(@RequestBody TokenCredential googleToken){
        return authSC.googleLogin(googleToken);
    }
}