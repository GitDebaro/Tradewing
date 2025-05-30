package com.tradewing.servicetests;

import com.tradewing.models.UserEntity;
import com.tradewing.services.impl.UserServiceImpl;
import com.tradewing.repos.UserRepo;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Mock
    private UserRepo usrRepo;

    @InjectMocks
    private UserServiceImpl userSC;

    private UserEntity testUser;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@user.com");
        testUser.setPassword("sha256");

    }

    @Test
    void getAllUsersTest(){
        when(usrRepo.findAll()).thenReturn(List.of(testUser));

        List<UserEntity> result = userSC.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getName());
        verify(usrRepo).findAll();
    }
    
    @Test
    void getAllUsersEmptyDDBB() {
        when(usrRepo.findAll()).thenReturn(List.of());

        List<UserEntity> result = userSC.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(usrRepo).findAll();
    }

}