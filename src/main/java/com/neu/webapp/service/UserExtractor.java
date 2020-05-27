package com.neu.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.webapp.JwtTokenUtil;
import com.neu.webapp.model.User;

@Service
public class UserExtractor {
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	UserDetailService userService;
 public User getUserFromtoken(String requestTokenHeader) {
	    String email = null;
		String jwtToken = null;
		if (requestTokenHeader != null) {
		
			try {
				
				email = jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println("JWT Token has expired");
			}
		} else {
			//logger.warn("JWT Token does not begin with Bearer String");
		}

//userService.addUser(user);
User user=userService.loadUserByemail(email);
return user;
 }
}
