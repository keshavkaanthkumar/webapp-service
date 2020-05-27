package com.neu.webapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.neu.webapp.model.User;
import com.neu.webapp.model.UserDTO;

/**
 * @author Keshav
 *
 */
public interface UserDetailService extends UserDetailsService{

	User loadUserByemail(String email) throws UsernameNotFoundException;
	
}

