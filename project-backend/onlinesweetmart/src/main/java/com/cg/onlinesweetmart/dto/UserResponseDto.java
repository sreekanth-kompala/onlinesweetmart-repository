package com.cg.onlinesweetmart.dto;

import java.util.Set;

import com.cg.onlinesweetmart.entity.SweetOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

	private long userId;
	
	private String username;
	
	private String userEmail;
	
	private Set<SweetOrder> sweetOrder;
}
