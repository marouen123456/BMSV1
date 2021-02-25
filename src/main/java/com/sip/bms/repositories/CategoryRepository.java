package com.sip.bms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sip.bms.entities.Book;
import com.sip.bms.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query("FROM Book b WHERE b.category.id_category = ?1")
	List<Book> findBooksByCategory(int id);
}
