package com.cg.onlinesweetmart.serviceimpltest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;

import com.cg.onlinesweetmart.dto.LoginDto;
import com.cg.onlinesweetmart.dto.RegisterDto;
import com.cg.onlinesweetmart.entity.Cart;
import com.cg.onlinesweetmart.entity.Role;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CartRepository;
import com.cg.onlinesweetmart.repository.RoleRepository;
import com.cg.onlinesweetmart.repository.UserRepository;
import com.cg.onlinesweetmart.security.JwtTokenProvider;
import com.cg.onlinesweetmart.service.impl.AuthServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    private LoginDto loginDto;
    private RegisterDto registerDto;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto("testUser_1", "testPassword");
        registerDto = new RegisterDto("testUser", "test@example.com", "testPassword", "testPassword");
    }

    // Test cases will be added here
    @Test
    void testLogin_Success() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn("jwtToken");

        String token = authServiceImpl.login(loginDto);

        assertEquals("jwtToken", token);
    }
    
    @Test
    void testRegister_Success() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(anyString())).thenReturn(new Role(1,"ROLE_CUSTOMER"));

        String message = authServiceImpl.register(registerDto);

        assertEquals("User Registered Successfully", message);
        verify(userRepository).save(any(User.class));
        verify(cartRepository).save(any(Cart.class));
    }
    
    @Test
    void testRegister_PasswordMismatch() {
        registerDto.setConfirmPassword("differentPassword");

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            authServiceImpl.register(registerDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("password did not match", exception.getMessage());
    }
    
    @Test
    void testRegister_UsernameExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            authServiceImpl.register(registerDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("username already exists", exception.getMessage());
    }
    
    @Test
    void testRegister_EmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            authServiceImpl.register(registerDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("email already exists", exception.getMessage());
    }


   

    
}