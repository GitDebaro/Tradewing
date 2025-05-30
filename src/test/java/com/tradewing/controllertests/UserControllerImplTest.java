package com.tradewing.controllers.impl;

import com.tradewing.models.UserEntity;
import com.tradewing.services.UserService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ReturnsUserList() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("test@user.com");
        user1.setPassword("sha256");

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setName("Test User 2");
        user2.setEmail("test2@user.com");
        user2.setPassword("sha256");

        List<UserEntity> mockUsers = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(mockUsers);

        List<UserEntity> result = userController.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("test@user.com", result.get(0).getEmail());
        assertEquals("test2@user.com", result.get(1).getEmail());
    }
}