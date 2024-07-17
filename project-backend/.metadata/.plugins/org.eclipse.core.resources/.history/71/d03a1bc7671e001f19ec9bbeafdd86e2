package com.cg.onlinesweetmart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cg.onlinesweetmart.security.JwtAuthenticationEntryPoint;
import com.cg.onlinesweetmart.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	private JwtAuthenticationFilter authenticationFilter;

	// Password encoder bean to encrypt passwords
    @Bean
    static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    // Configuring security filter chain
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((authorize) -> {
				authorize.requestMatchers("/api/auth/**").permitAll(); // Allow access to authentication endpoints
				authorize.requestMatchers(HttpMethod.GET, "/api/products").permitAll(); // Allow GET requests to products endpoint
				authorize.requestMatchers(HttpMethod.GET, "/api/categories").permitAll(); // Allow GET requests to categories endpoint
				authorize.anyRequest().authenticated(); // Require authentication for any other request
			})
			.httpBasic(Customizer.withDefaults()); // Use basic authentication
		
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint)); // Set custom authentication entry point
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT authentication filter before UsernamePasswordAuthenticationFilter
		
		return http.build();
	}
	
	// Authentication manager bean
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
