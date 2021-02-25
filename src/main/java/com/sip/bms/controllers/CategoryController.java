package com.sip.bms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sip.bms.entities.Category;
import com.sip.bms.repositories.CategoryRepository;

@Controller
@RequestMapping("/categories/")
public class CategoryController {

	private final CategoryRepository categoryRepository;
	@Autowired
	public CategoryController (CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
	}
	@GetMapping("list")
	public String listCategories (Model model) {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		if(categories.size()==0)
			categories=null;
		model.addAttribute("categories",categories);
		return ("/categories/listCategories");
	}
	@GetMapping("add")
	public String showAddCategoryForm(Category category) {
		return ("/categories/addCategories");
	}
	@PostMapping("add")
	public String saveAddedCategory(@Valid Category category, BindingResult result) {
		if(result.hasErrors())
			return ("/categories/addCategories");
		categoryRepository.save(category);
		return ("redirect:list");
	}
	
	@GetMapping("delete/{id}")
	public String deleteCategory(@PathVariable("id") int id){
		Category category = categoryRepository.findById(id)
								.orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
		categoryRepository.delete(category);
		return ("redirect:../list");
	}
}
