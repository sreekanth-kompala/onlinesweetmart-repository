package com.cg.onlinesweetmart.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sweet_orders")
public class SweetOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sweetOrderId;  // Unique identifier for the sweet order
	
	@Column(name = "order_date")
	private LocalDate orderDate;  // Date when the order is placed
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;  // User who placed the order
	
	private Double totalCost;  // Total cost of the order
	
	@OneToMany
	@JoinColumn(name = "products_list")
	private List<Product> listProduct;  // List of products in the order
 
    @PrePersist
    protected void onCreate() {
    	orderDate = LocalDate.now();  // Set the order date to the current date when the order is created
    }
}
