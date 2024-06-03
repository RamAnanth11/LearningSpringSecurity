package com.practice.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.practice.model.User;
import com.practice.practice.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
//	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("User 404");

		return new UserDetailsImplementation(user);
	}

//	public UserDetails loadUserByUsernameAndPassword(String username, String password) throws Exception {
//		User user = userRepository.findByUsername(username);
//		if (user == null)
//			throw new UsernameNotFoundException("User 404");
//		else {
//			boolean match = bCryptPasswordEncoder.matches(password, user.getPassword());
//			if (match)
//				return new UserDetailsImplementation(user);
//			else
//				throw new Exception("Password is wrong");
//		}
//
//	}

}
