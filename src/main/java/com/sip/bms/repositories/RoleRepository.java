package com.sip.bms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sip.bms.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Integer>{

	//@Query
	Role findByName(String name);
	
	
}
