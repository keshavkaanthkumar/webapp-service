package com.neu.webapp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neu.webapp.model.UserDTO;
import com.neu.webapp.repository.UserDao;
import com.neu.webapp.service.UserDetailsServiceImpl;

@RestController
public class UserController {
	@Autowired
	UserDao userDao;
	@Autowired 
	UserDetailsServiceImpl userDetailsService;
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> Update(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.update(user));
	}


}
