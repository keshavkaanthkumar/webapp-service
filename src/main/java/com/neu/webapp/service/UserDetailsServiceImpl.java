package com.neu.webapp.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neu.webapp.model.Cart;
import com.neu.webapp.model.PasswordReq;
import com.neu.webapp.model.UserDTO;
import com.neu.webapp.repository.CartDao;
import com.neu.webapp.repository.UserDao;






@Service
public class UserDetailsServiceImpl implements UserDetailService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private CartDao cartDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<com.neu.webapp.model.User> useropt = userDao.findById(email);
		com.neu.webapp.model.User user=useropt.get();
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
	@Override
	public com.neu.webapp.model.User loadUserByemail(String email) throws UsernameNotFoundException {
		Optional<com.neu.webapp.model.User> useropt = userDao.findById(email);
		com.neu.webapp.model.User user=useropt.get();
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
		return user;
	}
	public com.neu.webapp.model.User save(UserDTO user) {
		if(userDao.existsById(user.getEmail()))
		{
			throw new RuntimeException("Username already exists");
		}
		else
		{
		com.neu.webapp.model.User newUser = new com.neu.webapp.model.User();
		newUser.setLastname(user.getLastname());
		newUser.setFirstname(user.getFirstname());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		Cart cart =new Cart();
		cart.setUser(newUser);
		userDao.save(newUser);
		cartDao.save(cart);
		return  newUser;
		}
	}
	public com.neu.webapp.model.User update(UserDTO user) throws UsernameNotFoundException {
		 
		
			Optional<com.neu.webapp.model.User>olduser= userDao.findById(user.getEmail());
			com.neu.webapp.model.User oldUser=olduser.get();
			if (oldUser == null) {
				throw new UsernameNotFoundException("User not found with username: " + user.getEmail());
			}
		
		if(oldUser!=null) {
			oldUser.setLastname(user.getLastname());
			oldUser.setFirstname(user.getFirstname());
			oldUser.setEmail(user.getEmail());
			//oldUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		}
		return userDao.save(oldUser);
		
	    
	}
	public com.neu.webapp.model.User updatePassword(com.neu.webapp.model.User user,String newPwd) throws UsernameNotFoundException {
		 if(user!=null) {
		user.setPassword(bcryptEncoder.encode(newPwd));
		 }
		 else
		 {
				throw new UsernameNotFoundException("User not found ");

		 }
	
		 return userDao.save(user);
	
    
}
}