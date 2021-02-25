package com.sip.bms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sip.bms.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//@Query
	User findUserByEmail(String Email);
	User findById(long id);
		
}
