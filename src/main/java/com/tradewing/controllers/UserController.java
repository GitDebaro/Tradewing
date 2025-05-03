package com.tradewing.controllers;

import com.tradewing.models.UserEntity;
import com.tradewing.dto.LoginRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping("/")
    List<UserEntity> getAllUsers();	

    @PostMapping("/addUser")
    void addUser(@RequestBody UserEntity user);

    @PostMapping("/loginUser")
    String login(@RequestBody LoginRequest request); //cambiar más adelante por ResponseEntity<?>
    

}