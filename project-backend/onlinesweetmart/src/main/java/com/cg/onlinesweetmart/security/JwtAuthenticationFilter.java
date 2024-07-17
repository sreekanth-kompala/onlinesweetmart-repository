package com.cg.onlinesweetmart.security;

import java.io.IOException;

import org.apache.catalina.util.StringUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	/**
     * Filters incoming requests to authenticate with JWT token.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException if there is an error in the servlet
     * @throws IOException      if there is an I/O error
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getTokenFromRequest(request);
		
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			// Extract username from token
			String username = jwtTokenProvider.getUsername(token);
			// Load user details from database
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			// Create authentication object
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// Set authentication in security context
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		// Continue filter chain
		filterChain.doFilter(request, response);
		
	}
	
	/**
     * Extracts JWT token from request header.
     *
     * @param request the HTTP servlet request
     * @return the JWT token or null if not found
     */
	private String getTokenFromRequest(HttpServletRequest request) {
		
		String barerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(barerToken) && barerToken.startsWith("Bearer ")) {
			
			return barerToken.substring(7, barerToken.length());
		}
		
		return null;
	}

}
