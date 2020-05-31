package com.neu.webapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.User;

public interface BookDao extends CrudRepository<Book, Integer> {


}
