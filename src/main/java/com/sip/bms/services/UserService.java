package com.sip.bms.services;

import java.util.Optional;

//import java.util.Arrays;
//import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sip.bms.entities.Book;
import com.sip.bms.entities.Role;
import com.sip.bms.entities.User;
import com.sip.bms.repositories.BookRepository;
import com.sip.bms.repositories.RoleRepository;
import com.sip.bms.repositories.UserRepository;



@Service
public class UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BookRepository bookRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, 
						RoleRepository roleRepository, BookRepository bookRepository,
						BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bookRepository = bookRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	public void saveReservation(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByName("USER");
		user.setRole(userRole);
		userRepository.save(user);
	}
	
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByName("USER");
		user.setRole(userRole);
		long noBookId=8;
		Book book = bookRepository.findById(noBookId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Book Id:" + noBookId));
		user.setBook(book);
		userRepository.save(user);
	}
	
	public void addUserRole (Role role) {
		role.setName("USER");
		roleRepository.save(role);
	}
}