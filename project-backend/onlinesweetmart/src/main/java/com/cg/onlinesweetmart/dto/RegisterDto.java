package com.cg.onlinesweetmart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

	@NotBlank(message = "username is required")
    @Size(min = 6, max = 50, message = "minimum 6 characters required")
	private String username;
	
	@NotBlank(message = "email is required")
    @Email(message = "email should be valid")
	private String email;
	
	@NotBlank(message = "password is required")
    @Size(min = 6, message = "minimum 6 characters required")
	private String password;
	
	@NotBlank(message = "password confirmation is required")
	private String confirmPassword;
}
