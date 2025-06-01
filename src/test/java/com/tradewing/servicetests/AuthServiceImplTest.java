package com.tradewing.servicetests;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.tradewing.models.UserEntity;
import com.tradewing.services.impl.AuthServiceImpl;
import com.tradewing.services.JWTUtils;
import com.tradewing.repos.UserRepo;
import com.tradewing.dto.TokenCredential;

import org.mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private GoogleIdToken mockIdToken;

    @Mock
    private Payload mockPayload;

    @Spy
    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserEntity();
        user.setEmail("test@example.com");
    }

    @Test
    void googleLoginSuccessReturnsStringToken() throws Exception {
        TokenCredential tokenCredential = new TokenCredential();
        tokenCredential.setToken("valid-token");

        GoogleIdToken mockIdToken = mock(GoogleIdToken.class);
        Payload mockPayload = mock(GoogleIdToken.Payload.class);
        when(mockIdToken.getPayload()).thenReturn(mockPayload);

        doReturn(mockIdToken).when(authService).verifyGoogleToken("valid-token");
        when(authService.findOrCreateUser(mockPayload)).thenReturn(user);
        when(jwtUtils.createToken("test@example.com")).thenReturn("jwt-token");

        ResponseEntity<?> response = authService.googleLogin(tokenCredential);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwt-token", response.getBody());
    }

    @Test
    void googleLoginInvalidTokenReturnsUnauthorized() throws Exception {
        TokenCredential tokenCredential = new TokenCredential();
        tokenCredential.setToken("invalid-token");

        doReturn(null).when(authService).verifyGoogleToken("invalid-token");

        ResponseEntity<?> response = authService.googleLogin(tokenCredential);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void googleLoginFindOrCreateUserExceptionReturnsUnauthorized() throws Exception {
        TokenCredential tokenCredential = new TokenCredential();
        tokenCredential.setToken("valid-token");

        GoogleIdToken mockIdToken = mock(GoogleIdToken.class);
        Payload mockPayload = mock(GoogleIdToken.Payload.class);
        when(mockIdToken.getPayload()).thenReturn(mockPayload);

        doReturn(mockIdToken).when(authService).verifyGoogleToken("valid-token");
        doThrow(new RuntimeException("DB error")).when(authService).findOrCreateUser(mockPayload);

        ResponseEntity<?> response = authService.googleLogin(tokenCredential);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void googleLoginWhenJwtCreationFailsReturnsUnauthorized() throws Exception {
        TokenCredential tokenCredential = new TokenCredential();
        tokenCredential.setToken("valid-token");

        GoogleIdToken mockIdToken = mock(GoogleIdToken.class);
        Payload mockPayload = mock(GoogleIdToken.Payload.class);
        when(mockIdToken.getPayload()).thenReturn(mockPayload);

        doReturn(mockIdToken).when(authService).verifyGoogleToken("valid-token");
        when(authService.findOrCreateUser(mockPayload)).thenReturn(user);
        when(jwtUtils.createToken("test@example.com")).thenThrow(new RuntimeException("JWT error"));

        ResponseEntity<?> response = authService.googleLogin(tokenCredential);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void findOrCreateUserReturnsExistingUser() {
        GoogleIdToken.Payload payload = mock(GoogleIdToken.Payload.class);
        when(payload.getEmail()).thenReturn("existing@example.com");

        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("existing@example.com");

        when(userRepo.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUser));

        UserEntity result = authService.findOrCreateUser(payload);

        assertNotNull(result);
        assertEquals("existing@example.com", result.getEmail());
        verify(userRepo, never()).save(any());
    }

    @Test
    void findOrCreateUser_CreatesNewUser_WhenEmailDoesNotExist() {
        GoogleIdToken.Payload payload = mock(GoogleIdToken.Payload.class);
        when(payload.getEmail()).thenReturn("new@example.com");
        when(payload.get("given_name")).thenReturn("John");
        when(payload.get("family_name")).thenReturn("Doe");
        when(payload.get("picture")).thenReturn("http://example.com/image.jpg");

        when(userRepo.findByEmail("new@example.com")).thenReturn(Optional.empty());

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);

        UserEntity savedUser = new UserEntity();
        savedUser.setEmail("new@example.com");

        when(userRepo.save(any(UserEntity.class))).thenReturn(savedUser);

        UserEntity result = authService.findOrCreateUser(payload);

        verify(userRepo).save(userCaptor.capture());
        UserEntity capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals("new@example.com", capturedUser.getEmail());
        assertEquals("John", capturedUser.getName());
        assertEquals("Doe", capturedUser.getSurname());
        assertEquals("http://example.com/image.jpg", capturedUser.getImage());
        assertEquals("GOOGLE-USER", capturedUser.getPassword());
        assertEquals("new@example.com", result.getEmail());
    }
}