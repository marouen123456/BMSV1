package com.sip.bms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sip.bms.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	//@Query
	//Book findBookById(long id_book);
	Book findByTitle(String title);
}
