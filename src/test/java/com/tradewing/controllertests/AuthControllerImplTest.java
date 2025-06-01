package com.tradewing.controllertests;

import org.springframework.http.ResponseEntity;

import com.tradewing.services.AuthService;
import com.tradewing.controllers.impl.AuthControllerImpl;
import com.tradewing.dto.TokenCredential;

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

class AuthServiceImplTest{

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthControllerImpl authController;  

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    void testGoogleLoginReturnsExpectedResponse() {
        TokenCredential mockToken = new TokenCredential();
        String webToken = "eyTestToken";
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(webToken);

        when(authService.googleLogin(mockToken)).thenReturn((ResponseEntity) expectedResponse);

        ResponseEntity<?> actualResponse = authController.googleLogin(mockToken);

        assertNotNull(actualResponse);
        assertEquals(200,actualResponse.getStatusCodeValue());
        assertEquals("eyTestToken", actualResponse.getBody());
        verify(authService, times(1)).googleLogin(mockToken);
    }
}