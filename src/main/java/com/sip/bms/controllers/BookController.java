package com.sip.bms.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;

import com.sip.bms.entities.Book;
import com.sip.bms.entities.Category;
import com.sip.bms.repositories.BookRepository;
import com.sip.bms.repositories.CategoryRepository;

@Controller
@RequestMapping("/books/")
public class BookController {
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/target/classes/static/dist/img";
	
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	@Autowired
	public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
		this.bookRepository=bookRepository;
		this.categoryRepository=categoryRepository;
	}
	@GetMapping("data")
	public String test() {
		return "books/data";
	}
	@GetMapping("list")
	public String booksList(Model model) {
		List<Book> listBooks = (List<Book>) bookRepository.findAll();
		if(listBooks.size()==0)
			listBooks=null;
		model.addAttribute("listBooks", listBooks);
		
		return ("books/listBooks");
	}
	@GetMapping("add")
	//@ResponseBody
	public String showAddBookForm( Model model) {
		List<Category> listCategories = (List<Category>) categoryRepository.findAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("book", new Book());
		return ("books/addBook");
	}
	
	@PostMapping("add")
	//@ResponseBody
	public String addBook(@Valid Book book, BindingResult result,
						@RequestParam(name="categoryId", required = false) int categoryId,
						@RequestParam(name= "cover", required = false) MultipartFile cover, Model model) {	
		/*
		if (result.hasErrors())	{
			//List<Category> listCategories = (List<Category>) categoryRepository.findAll();
			model.addAttribute("listCategories", categoryRepository.findAll());
			System.out.println(result.getErrorCount());
			System.out.println(result.getAllErrors());
			//System.out.println(categoryId);
			return "books/addBook";
		}
		*/
			StringBuilder fileName = new StringBuilder();
			//MultipartFile file = cover[0];
			Random rand = new Random();
			int x = rand.nextInt(6) + 5;
		
			fileName.append(x).append(cover.getOriginalFilename());
		
			Path fileNameAndPath = Paths.get(uploadDirectory, fileName.toString());
				try {
					Files.write(fileNameAndPath, cover.getBytes());
					} 
				catch (IOException e) {
					e.printStackTrace();
					}
				book.setCover(fileName.toString());
		
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(() -> new IllegalArgumentException("Invalid categorie Id:" + categoryId));
		book.setCategory(category);
		bookRepository.save(book);
		return ("redirect:list2");	
		
	}
	
	
	@GetMapping("detail/{id}")
	public String showBookDetails(@PathVariable("id") long id, Model model) {
		Book book = bookRepository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid Book Id:" + id));
		model.addAttribute("book", book);
		return ("books/detailBook");
	}
	@GetMapping("list2")
	public String showBookIndex(Model model) {
		List<Book> listBooks = (List<Book>) bookRepository.findAll();
		if(listBooks.size()==0)
			listBooks=null;
		model.addAttribute("listBooks", listBooks);
		return ("books/2ndListBook");
	}
}
