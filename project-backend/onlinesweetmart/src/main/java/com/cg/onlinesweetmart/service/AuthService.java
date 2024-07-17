package com.cg.onlinesweetmart.service;

import com.cg.onlinesweetmart.dto.LoginDto;
import com.cg.onlinesweetmart.dto.RegisterDto;

public interface AuthService {

	/**
	 * Authenticate user and generate JWT token.
	 *
	 * @param loginDto contains username or email and password
	 * @return JWT token
	 */
	String login(LoginDto loginDto);

	/**
	 * Register a new user.
	 *
	 * @param registerDto contains user registration details
	 * @return registration success message
	 */
	String register(RegisterDto registerDto);

	
}
