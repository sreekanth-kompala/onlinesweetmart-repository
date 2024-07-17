package com.cg.onlinesweetmart.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private String jwtSecret = "daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";
	
	private long jwtExpirationDate = 604800000;
	
	// Generates a JWT token based on the user's authentication details
	public String generateToken(Authentication authentication) {
		
		// Retrieve the username from the authentication object
		String username = authentication.getName();
		
		Date currentDate = new Date();
		
		Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		// Build the JWT token with subject (username), issued date, expiration date, and sign it with the secret key
		String token = Jwts.builder()
							.setSubject(username)
							.setIssuedAt(new Date())
							.setExpiration(expirationDate) // Sign the token with the secret key
							.signWith(key())
							.compact();
		
		return token;
		
	}
	
    // Generates the key used for signing the JWT, decoding the base64-encoded secret
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	// Extracts the username from the given JWT token
	public String getUsername(String token) {
		// Parse the claims (payload) from the token
		Claims claims = Jwts.parser()
							.setSigningKey(key()) // Set the key used for parsing the token
							.build()
							.parseClaimsJws(token)
							.getBody();
			
		// Retrieve the subject (username) from the claims
		String username = claims.getSubject();
		
		return username;
	}
	
	// Validates the given JWT token to ensure it is correctly signed and not expired
	public boolean validateToken(String token) {
		// Parse the token to check if it's valid (signature and expiration are checked)
		Jwts.parser()
			.setSigningKey(key()) // Set the key used for parsing the token
			.build()
			.parse(token);
		
		return true; //token is valid
	}
	
}
