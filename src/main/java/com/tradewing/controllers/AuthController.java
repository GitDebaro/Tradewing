package com.tradewing.controllers;

import com.tradewing.dto.TokenCredential;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/google")
    ResponseEntity<?> googleLogin(@RequestBody TokenCredential googleToken);
}