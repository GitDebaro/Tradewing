package com.tradewing.servicetests;

import org.springframework.http.ResponseEntity;

import com.tradewing.models.UserEntity;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.impl.UserServiceImpl;
import com.tradewing.repos.ProductRepo;
import com.tradewing.services.JWTUtils;
import com.tradewing.repos.UserRepo;
import com.tradewing.dto.UpdateUserPayload;
import com.tradewing.dto.UserInfo;

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

    @Mock
    private JWTUtils jwt;

    @Mock
    private ProductRepo productSC;

    @InjectMocks
    private UserServiceImpl userSC;

    private UserEntity testUser;
    private UserEntity newUser;
    private ProductEntity product1;
    private ProductEntity product2;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@user.com");
        testUser.setPassword("sha256");

        newUser = new UserEntity();        
        newUser.setId(2L);
        newUser.setName("Test User");
        newUser.setEmail("test@user.com");
        newUser.setPassword("sha256");

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
    */
    
   @Test
    void getMyInventoryReturnsList() {
        String token = "valid-token";
        List<ProductEntity> products = List.of(product1,product2);

        when(jwt.decode(token)).thenReturn(testUser);
        when(productSC.findBySeller(testUser)).thenReturn(products);

        List<ProductEntity> result = userSC.getMyInventory(token);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("https://placehold.co/200x200?text=Producto1",result.get(0).getImage());
        assertEquals("Product Test 2", result.get(1).getName());
        assertEquals("https://placehold.co/400x400?text=Producto2",result.get(1).getImage());
        verify(productSC).findBySeller(testUser);
    }

    @Test
    void getMyInventoryInvalidTokenReturnsEmptyList() {
        String token = "invalid-token";
        when(jwt.decode(token)).thenReturn(null);

        List<ProductEntity> result = userSC.getMyInventory(token);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jwt).decode(token);
    }

    @Test
    void getMyInventory_exceptionReturnsEmptyList() {
        String token = "any-token";
        when(jwt.decode(token)).thenThrow(new RuntimeException());

        List<ProductEntity> result = userSC.getMyInventory(token);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jwt).decode(token);
    }

    @Test
    void updateUserDataReturnsResponse() {
        UpdateUserPayload payload = new UpdateUserPayload();
        payload.setToken("valid-token");
        payload.setImage("new-image");

        when(jwt.decode(payload.getToken())).thenReturn(testUser);
        when(usrRepo.save(testUser)).thenReturn(testUser);

        ResponseEntity<UserInfo> response = userSC.updateUserData(payload);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test User", response.getBody().getName());
        verify(usrRepo).save(testUser);
    }

    @Test
    void updateUserDataNotFoundReturnsResponse() {
        UpdateUserPayload payload = new UpdateUserPayload();
        payload.setToken("invalid-token");

        when(jwt.decode(payload.getToken())).thenReturn(null);

        ResponseEntity<UserInfo> response = userSC.updateUserData(payload);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(jwt).decode(any(String.class));
    }

    @Test
    void updateUserDataExceptionBadRequest() {
        UpdateUserPayload payload = new UpdateUserPayload();
        payload.setToken("invalid-token");

        when(jwt.decode(payload.getToken())).thenThrow(new RuntimeException());

        ResponseEntity<UserInfo> response = userSC.updateUserData(payload);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        verify(jwt).decode(any(String.class));
    }

}