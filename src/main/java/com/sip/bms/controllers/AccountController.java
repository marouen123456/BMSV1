package com.sip.bms.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.bms.entities.Book;
import com.sip.bms.entities.Role;
import com.sip.bms.entities.User;
import com.sip.bms.repositories.BookRepository;
import com.sip.bms.repositories.CategoryRepository;
import com.sip.bms.repositories.RoleRepository;
import com.sip.bms.repositories.UserRepository;
import com.sip.bms.services.UserService;
//import com.sip.bms.services.UserService;

@Controller
@RequestMapping("/account/")
public class AccountController {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	public AccountController (UserRepository userRepository, RoleRepository roleRepository,
			CategoryRepository categoryRepository, BookRepository bookRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping("list")
	public String showReservationList (Model model) {
		List<User> listResrevation = (List<User>)userRepository.findAll();
		if (listResrevation.size()==0)
			listResrevation=null;
		model.addAttribute("listResrevation", listResrevation);
		return "reservation/listReservation";
	}
	
	@GetMapping("list2")
	public String showReservationList2 (Model model) {
		List<User> listResrevation = (List<User>)userRepository.findAll();
		if (listResrevation.size()==0)
			listResrevation=null;
		model.addAttribute("listResrevation", listResrevation);
		return "reservation/listReservation2";
	}
	
	@GetMapping("add/{id}")
	public String showAddAccountForm(@PathVariable("id") long id, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Book Id:" + id));
		model.addAttribute("book", book);
		return "reservation/addReservation";
	}
	
	@GetMapping("add2/{id}")
	public String showAddAccountForm2(@PathVariable("id") long id, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Book Id:" + id));
		model.addAttribute("book", book);
		return "reservation/addReservation2";
	}
	
	@PostMapping("add")
	public String saveaddedAccount (@Valid User user, @RequestParam(name= "bookId") long bookId,
								@RequestParam(name= "bookTitle") String bookTitle) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Book Id:" + bookId));
		user.setBook(book);
		sendEmail(user.getEmail(), user.getBook().getTitle());
		userService.saveReservation(user);
		return "redirect:../books/list2";
	}
	
	void sendEmail(String email, String bookTitle) {
		 SimpleMailMessage msg = new SimpleMailMessage();
		 msg.setTo(email);
		 
		 msg.setSubject("Reservation de Livre");
		 msg.setText("Bonjour Mr, Mme, Votre livre " + bookTitle +"est reservé pour votre compte pendant 7 jours à partir de cette date"
		 + " \n Bonne lecture");
		 
		 javaMailSender.send(msg);
	}
}
