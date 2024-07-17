package com.cg.onlinesweetmart.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.cg.onlinesweetmart.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Authenticate user and generate JWT token.
     *
     * @param loginDto contains username or email and password
     * @return JWT token
     */
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    /**
     * Register a new user.
     *
     * @param registerDto contains user registration details
     * @return registration success message
     */
    @Override
    public String register(RegisterDto registerDto) {

        // Check if password and confirm password match
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Password and confirm password do not match");
        }

        // Check if username already exists
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // Create new cart and user
        Cart cart = new Cart();
        User user = new User();

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setCart(cart);

        // Assign default role to the user
        Role role = roleRepository.findByName("ROLE_CUSTOMER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);

        // Save cart and user to the repository
        cartRepository.save(cart);
        userRepository.save(user);

        return "User Registered Successfully";
    }
}