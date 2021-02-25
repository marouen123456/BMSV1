package com.sip.bms;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.sip.bms.controllers.BookController;

@SpringBootApplication
public class BmsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new File(BookController.uploadDirectory).mkdir();
		SpringApplication.run(BmsApplication.class, args);
		System.out.println("Hello! bms project is running....");
	}

}
