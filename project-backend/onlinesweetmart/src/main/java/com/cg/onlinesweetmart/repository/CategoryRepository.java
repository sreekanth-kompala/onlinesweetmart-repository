package com.cg.onlinesweetmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlinesweetmart.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
