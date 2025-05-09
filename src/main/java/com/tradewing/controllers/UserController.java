package com.tradewing.controllers;

import com.tradewing.models.UserEntity;
import com.tradewing.dto.LoginRequest;
import com.tradewing.dto.LoginResponse;
import com.tradewing.dto.UserInfo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping("/")
    List<UserEntity> getAllUsers();	

    @PostMapping("/addUser")
    ResponseEntity<?> addUser(@RequestBody UserEntity user);

    @PostMapping("/loginUser")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request); 
    
    @PostMapping("/data")
    ResponseEntity<UserInfo> getUserData(@RequestBody LoginResponse userToken);
}