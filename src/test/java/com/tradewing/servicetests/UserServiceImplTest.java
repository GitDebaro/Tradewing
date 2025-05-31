package com.tradewing.servicetests;

import org.springframework.http.ResponseEntity;

import com.tradewing.models.UserEntity;
import com.tradewing.services.impl.UserServiceImpl;
import com.tradewing.repos.UserRepo;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
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

    @Test   
    void addUserSuccess(){
        UserEntity newUser = new UserEntity();        
        newUser.setId(2L);
        newUser.setName("Test User 2");
        newUser.setEmail("test2@user.com");
        newUser.setPassword("sha256");

        when(usrRepo.save(any(UserEntity.class))).thenReturn(newUser);

        ResponseEntity<?> response = userSC.addUser(newUser);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User created successfully", response.getBody());
        verify(usrRepo).save(any(UserEntity.class));
    }

    @Test
    void addUserConflict(){
        //Same user that was defined in the mock
        UserEntity newUser = new UserEntity();        
        newUser.setId(2L);
        newUser.setName("Test User");
        newUser.setEmail("test@user.com");
        newUser.setPassword("sha256");

        when(usrRepo.save(any(UserEntity.class))).thenThrow(new RuntimeException("duplicate key value violates unique constraint"));

        ResponseEntity<?> response = userSC.addUser(newUser);
        assertEquals(409, response.getStatusCodeValue());
        assertEquals("There is already an account linked to this email", response.getBody());
        verify(usrRepo).save(any(UserEntity.class));
    }

    @Test 
    void addUserDDBBNotAccesible(){        
        UserEntity newUser = new UserEntity();        
        newUser.setId(2L);
        newUser.setName("Test User 2");
        newUser.setEmail("test2@user.com");
        newUser.setPassword("sha256");

        when(usrRepo.save(any(UserEntity.class))).thenThrow(new RuntimeException("FATAL: Max client connections reached"));

        ResponseEntity<?> response = userSC.addUser(newUser);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An error ocurred while creating a new User. Try again later", response.getBody());
        verify(usrRepo).save(any(UserEntity.class));
    }

    /*
    EXPECTED REFACTOR of Controller and Service
    TO DO: loginUserTests

    TO DO: userData tests

    EXPECTED REFACTOR: JWT Utils class 

    TO DO: myInventory

    TO DO: updateUser
    */
    

}