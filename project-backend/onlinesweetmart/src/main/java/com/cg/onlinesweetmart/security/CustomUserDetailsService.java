package com.cg.onlinesweetmart.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	/**
	 * Load user details by username or email.
	 *
	 * @param usernameOrEmail the username or email of the user
	 * @return UserDetails object representing the user
	 * @throws UsernameNotFoundException if the user is not found
	 */
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
		
		// Retrieve the user from the database by username or email
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
		
		// Map user roles to Spring Security's GrantedAuthority
		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		
		// Return a UserDetails object representing the user
		return new org.springframework.security.core.userdetails.User(
				usernameOrEmail,
				user.getPassword(),
				authorities
				);
	}
}
