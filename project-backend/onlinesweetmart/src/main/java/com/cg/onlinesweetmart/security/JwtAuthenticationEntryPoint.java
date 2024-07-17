package com.cg.onlinesweetmart.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	/**
     * Invoked when authentication fails and an unauthorized error response needs to be sent.
     *
     * @param request       the request that resulted in an AuthenticationException
     * @param response      the response to which the error should be written
     * @param authException the exception that caused the authentication failure
     * @throws IOException      in case of an I/O error when sending the response
     * @throws ServletException in case of any other error when sending the response
     */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// Send an unauthorized error response with the provided error message
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}

}
