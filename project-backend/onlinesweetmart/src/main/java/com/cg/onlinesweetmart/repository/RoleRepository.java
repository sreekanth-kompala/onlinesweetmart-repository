package com.cg.onlinesweetmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.onlinesweetmart.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	public Role findByName(String name);

}
