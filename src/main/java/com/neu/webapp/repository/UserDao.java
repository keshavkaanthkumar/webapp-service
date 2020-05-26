package com.neu.webapp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.neu.webapp.model.User;




@Repository
public interface UserDao extends CrudRepository<User, String> {
	
//	@Query(value = "select * from webapp.user where email = ?", nativeQuery = true)  
//	User findByemail(String email);
	
}