package com.neu.webapp;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neu.webapp.model.PasswordReq;
import com.neu.webapp.model.User;
import com.neu.webapp.model.UserDTO;
import com.neu.webapp.repository.UserDao;
import com.neu.webapp.service.UserDetailsServiceImpl;
import com.neu.webapp.service.UserExtractor;
import com.timgroup.statsd.StatsDClient;

@RestController
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	UserDao userDao;
	@Autowired 
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	UserExtractor userExtractor;
	@Autowired
	StatsDClient statsdclient;
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		long start = System.currentTimeMillis();
		User resuser=null;
		statsdclient.incrementCounter("Books created");
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	String	emailpattern="[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}";
		if(user.getEmail().matches(emailpattern)) {
	      if(user.getPassword().matches(pattern)) {
	    	  if(user.getLastname().matches(".*[a-zA-Z]+.*")&&user.getFirstname().matches(".*[a-zA-Z]+.*")){
	    	  try {
	    		  
	  			resuser=userDetailsService.save(user);
	  			long end = System.currentTimeMillis();
   			 long time = (end - start);
   		     statsdclient.recordExecutionTime("Register user API call",time);
	  		}
	  		catch(Exception ex){
	  			return ResponseEntity.badRequest().body(ex.getMessage().toString());
	  		}
	  		return ResponseEntity.ok(resuser);
	    	  }else
	    	  {
	    		  return ResponseEntity.badRequest().body("Invalid first or last name");
	    	  }
	      }
	      else
	      {
	    	  return ResponseEntity.badRequest().body("Choose a password with the following constraints:\n" + 
	                "Minimum of 8 characters\n"+
	    	  		"the use of both upper-case and lower-case letters (case sensitivity)\n" + 
	    	  		"inclusion of one or more numerical digits\n" + 
	    	  		"inclusion of special characters, such as @, #, $");
	    	 
	      }
		}
		else
		{
			 return ResponseEntity.badRequest().body("Invalid email Id");
		}
		
	}
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestParam(name = "email") String email) throws Exception {
	       
		Object obj=userDetailsService.loadUserByemail(email);
	  		return ResponseEntity.ok(obj);
	    
		
	}
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> Update(@RequestBody UserDTO user) throws Exception {
		if(user.getFirstname().trim().isEmpty()||user.getLastname().trim().isEmpty()) {
			
			return ResponseEntity.badRequest().body("First and last name cannot be empty");
		}
		User resuser;
		try {	    	  if(user.getLastname().matches(".*[a-zA-Z]+.*")&&user.getFirstname().matches(".*[a-zA-Z]+.*")){

			
			resuser=userDetailsService.update(user);
		}
		else
		{
  		  return ResponseEntity.badRequest().body("Invalid first or last name");

		}
		}
		catch(Exception ex){
			return ResponseEntity.badRequest().body(ex.getMessage().toString());
		}
		return ResponseEntity.ok(resuser);
	}
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseEntity<?> passwordUpdate(@RequestBody PasswordReq passwordReq,@RequestHeader("Authorization") String token) throws Exception {
		
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		if(passwordReq.getNewPassword().matches(pattern)) {User requser;
		try {
			requser= userExtractor.getUserFromtoken(token);
			authenticate(requser.getEmail(), passwordReq.getOldPassword());
			
		}
		catch(Exception ex){
			return ResponseEntity.badRequest().body(ex.getMessage().toString());
		}
		User resUser;
		try {
			resUser=userDetailsService.updatePassword(requser, passwordReq.getNewPassword());
		}
		catch(Exception ex){
			return ResponseEntity.badRequest().body(ex.getMessage().toString());
		}
		return ResponseEntity.ok(resUser);}
		else
		{
			 return ResponseEntity.badRequest().body("Choose a password with the following constraints:\n" + 
		                "Minimum of 8 characters\n"+
		    	  		"the use of both upper-case and lower-case letters (case sensitivity)\n" + 
		    	  		"inclusion of one or more numerical digits\n" + 
		    	  		"inclusion of special characters, such as @, #, $");
		}
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}


}
