package com.tradewing.controllertests;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.tradewing.models.UserEntity;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.UserService;
import com.tradewing.controllers.impl.UserControllerImpl;
import com.tradewing.dto.TokenCredential;
import com.tradewing.dto.UpdateUserPayload;
import com.tradewing.dto.UserInfo;
import com.tradewing.dto.LoginRequest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserControllerImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;
    UserEntity user1;
    UserEntity user2;
    ProductEntity product1;
    ProductEntity product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("test@user.com");
        user1.setPassword("sha256");

        user2 = new UserEntity();
        user2.setId(1L);
        user2.setName("Test User 2");
        user2.setEmail("test2@user.com");
        user2.setPassword("sha256");

        product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("Product Test");
        product1.setDescription("Test");
        product1.setImage("https://placehold.co/200x200?text=Producto1");

        product2 = new ProductEntity();
        product2.setId(2L);
        product2.setName("Product Test 2");
        product2.setDescription("Test");
        product2.setImage("https://placehold.co/400x400?text=Producto2");
    }

    @Test
    void getAllUsersReturnsList() {
        List<UserEntity> mockUsers = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(mockUsers);

        List<UserEntity> result = userController.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("test@user.com", result.get(0).getEmail());
        assertEquals("test2@user.com", result.get(1).getEmail());
    }

    @Test
    void addUserReturnsResponse(){
        ResponseEntity<?> response = new ResponseEntity<>("User created successfully", HttpStatus.CREATED);

        when(userService.addUser(any(UserEntity.class))).thenReturn((ResponseEntity) response);

        ResponseEntity<?> result = userController.addUser(user1);

        assertNotNull(result);
        assertEquals(201, result.getStatusCodeValue());
        assertEquals("User created successfully", result.getBody());
        verify(userService, times(1)).addUser(any(UserEntity.class));
    }

    @Test
    void LoginSuccessReturnsResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        TokenCredential mockToken = new TokenCredential("mocked-token");
        when(userService.authenticate(request.getEmail(), request.getPassword())).thenReturn(ResponseEntity.ok(mockToken));

        ResponseEntity<TokenCredential> response = userController.login(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mocked-token", response.getBody().getToken());
        verify(userService).authenticate(request.getEmail(),request.getPassword());
    }

    @Test
    void testGetUserData_Successful() {
        TokenCredential tokenCredential = new TokenCredential("mocked-token");
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setName("John");
        mockUserInfo.setSurname("Doe");
        mockUserInfo.setEmail("john@example.com");

        when(userService.getUserData(tokenCredential.getToken())).thenReturn(ResponseEntity.ok(mockUserInfo));

        ResponseEntity<UserInfo> response = userController.getUserData(tokenCredential);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getName());
        assertEquals("Doe", response.getBody().getSurname());
        assertEquals("john@example.com", response.getBody().getEmail());
        verify(userService).getUserData(tokenCredential.getToken());
    }

    @Test
    void getMyInventoryReturnsList() {
        TokenCredential token = new TokenCredential("token");
        List<ProductEntity> inventory = Arrays.asList(product1,product2);
        when(userService.getMyInventory("token")).thenReturn(inventory);

        List<ProductEntity> result = userController.getMyInventory(token);

        assertEquals(2, result.size());
        assertEquals("Product Test", result.get(0).getName());
        assertEquals("https://placehold.co/200x200?text=Producto1",result.get(0).getImage());
        assertEquals("Product Test 2", result.get(1).getName());
        assertEquals("https://placehold.co/400x400?text=Producto2",result.get(1).getImage());
        verify(userService).getMyInventory("token");
    }

    @Test
    void updateUserDataReturnsResponse(){
        UpdateUserPayload payload = new UpdateUserPayload();
        UserInfo updatedInfo = new UserInfo();
        ResponseEntity<UserInfo> expectedResponse = new ResponseEntity(updatedInfo,HttpStatus.OK);
        when(userService.updateUserData(payload)).thenReturn(expectedResponse);

        ResponseEntity<UserInfo> response = userController.updateUserData(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedInfo, response.getBody());
        verify(userService).updateUserData(payload);
    }

}