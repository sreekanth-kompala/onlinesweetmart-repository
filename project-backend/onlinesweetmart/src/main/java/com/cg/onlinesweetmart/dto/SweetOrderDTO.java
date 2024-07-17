package com.cg.onlinesweetmart.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class SweetOrderDTO {

	private int sweetOrderId;
	private long userId;
	private String orderDate;
	
}
