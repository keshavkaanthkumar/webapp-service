package com.neu.webapp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neu.webapp.model.User;
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
		User resuser=null;
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	      if(user.getPassword().matches(pattern)) {
	    	  try {
	  			resuser=userDetailsService.save(user);
	  		}
	  		catch(Exception ex){
	  			return ResponseEntity.badRequest().body(ex.getMessage());
	  		}
	  		return ResponseEntity.ok(resuser);
	      }
	      else
	      {
	    	  return ResponseEntity.badRequest().body("Invalid password");
	      }
		
	}
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestParam(name = "email") String email) throws Exception {
	
	   
	  		return ResponseEntity.ok(userDetailsService.loadUserByemail(email));
	    
		
	}
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> Update(@RequestBody UserDTO user) throws Exception {
		User resuser;
		try {
			resuser=userDetailsService.update(user);
		}
		catch(Exception ex){
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.ok(resuser);
	}


}
