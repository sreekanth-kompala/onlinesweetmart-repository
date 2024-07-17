package com.cg.onlinesweetmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlinesweetmart.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByCategoryCategoryId(int categoryId);

	List<Product> findByNameContaining(String name);

}
