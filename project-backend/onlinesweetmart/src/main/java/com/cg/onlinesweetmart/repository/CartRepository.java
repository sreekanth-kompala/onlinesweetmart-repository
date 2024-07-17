package com.cg.onlinesweetmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlinesweetmart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

}
