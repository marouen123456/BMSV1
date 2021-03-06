package com.sip.bms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sip.bms.entities.User;
import com.sip.bms.repositories.RoleRepository;
import com.sip.bms.repositories.UserRepository;
import com.sip.bms.services.UserService;

@Controller
@RequestMapping("/users/")
public class UserController {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserService userService;
	@Autowired
	public UserController(UserRepository userRepository, RoleRepository roleRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.userRepository=userRepository;
		this.roleRepository=roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping("list")
	public String showUsersList(Model model) {
		List<User> listUsers = (List<User>) userRepository.findAll();
		if(listUsers.size()==0)
			listUsers=null;
		model.addAttribute("listUsers", listUsers);
		return "Users/listUsers";
	}
	
	@GetMapping("add")
	public String showAddUsersForm(User user) {
		return "Users/addUser";
	}
	
	@PostMapping("add")
	public String saveAddedUser(@Valid User user) {
		userService.saveUser(user);
		return "redirect:list";
	}
}
