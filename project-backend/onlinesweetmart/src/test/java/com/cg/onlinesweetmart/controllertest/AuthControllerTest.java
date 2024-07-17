package com.cg.onlinesweetmart.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.onlinesweetmart.controller.AuthController;
import com.cg.onlinesweetmart.dto.JwtAuthResponse;
import com.cg.onlinesweetmart.dto.LoginDto;
import com.cg.onlinesweetmart.dto.RegisterDto;
import com.cg.onlinesweetmart.service.AuthService;

public class AuthControllerTest {

    // Mocking the AuthService dependency
    @Mock
    private AuthService authService;

    // Injecting mocks into the AuthController
    @InjectMocks
    private AuthController authController;

    // Initializing mocks before each test method
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Testing the register method of the AuthController
    @Test
    public void testRegister() {
        // Creating a RegisterDto object for testing
        RegisterDto registerDto = new RegisterDto("testuser", "test@example.com", "password", "password");
        // Expected response message
        String expectedResponse = "User registered successfully";

        // Mocking the behavior of the register method in the AuthService
        when(authService.register(registerDto)).thenReturn(expectedResponse);

        // Invoking the register method in the AuthController and capturing the response
        ResponseEntity<String> responseEntity = authController.register(registerDto);

        // Asserting that the HTTP status code is HttpStatus.CREATED (201)
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // Asserting that the response body matches the expected response
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    // Testing the login method of the AuthController
    @Test
    public void testLogin() {
        // Creating a LoginDto object for testing
        LoginDto loginDto = new LoginDto("testuser", "password");
        // Mocked JWT token
        String token = "mocked.jwt.token";
        // Creating a JwtAuthResponse object with the mocked token
        JwtAuthResponse expectedResponse = new JwtAuthResponse(token, token);

        // Mocking the behavior of the login method in the AuthService
        when(authService.login(loginDto)).thenReturn(token);

        // Invoking the login method in the AuthController and capturing the response
        ResponseEntity<JwtAuthResponse> responseEntity = authController.login(loginDto);

        // Asserting that the HTTP status code is HttpStatus.OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Asserting that the access token in the response body matches the expected token
        assertEquals(expectedResponse.getAccessToken(), responseEntity.getBody().getAccessToken());
    }
}


