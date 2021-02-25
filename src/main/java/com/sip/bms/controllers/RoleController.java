package com.sip.bms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.bms.entities.Role;
import com.sip.bms.repositories.RoleRepository;

@Controller
@RequestMapping("/roles/")
public class RoleController {

	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleController (RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	@GetMapping("list")
	//@ResponseBody
	public String showRoleList(Model model) {
		List<Role> listRoles = (List<Role>) roleRepository.findAll();
		System.out.println(listRoles);
		if (listRoles.size() == 0)
			listRoles = null;
		model.addAttribute("listRoles", listRoles);
		return "Roles/listRoles";
	}
	@GetMapping("add")
	//
	public String showAddRoleForm () {
		return "Roles/addRole";
	}
	@PostMapping("add")
	//
	public String saveAddedRole (@RequestParam(name = "name", required = false) String role) {
		Role Nrole = new Role(role);
		System.out.println(Nrole);
		roleRepository.save(Nrole);
		return "redirect:list";
	}
}
