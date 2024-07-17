package com.cg.onlinesweetmart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	@NotEmpty(message = "Product name is required")
	private String name;
	
	@NotEmpty(message = "Photo is required")
	private String photoPath;
	
	@NotNull(message = "Enter a valid price")
	@Min(value = 1, message = "The Value should be more than 1")
	private double price;
	
	@Size(
	        min = 10, max = 100,
	        message = "Address should have a length between 10 and 100 characters.")
	@NotNull(message = "Enter a valid Product Id")
	private String description;
	
	@NotNull(message = "Enter the availablity")
	@AssertTrue
	private boolean available;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
}
