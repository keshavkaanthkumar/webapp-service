package com.neu.webapp.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.User;

public interface BookDao extends CrudRepository<Book, Integer> {

	@Query(value = "select * from book ORDER BY isbn ASC, price ASC", nativeQuery = true)  
	ArrayList<Book> getAllBooks();


}
