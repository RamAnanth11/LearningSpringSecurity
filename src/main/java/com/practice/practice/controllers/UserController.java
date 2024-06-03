package com.practice.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.model.User;
import com.practice.practice.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository repo;

	@GetMapping("/create")
	public String createUser(@RequestBody User user) {
		if (user == null)
			return "No User";
		else {
			BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
			user.setPassword(crypt.encode(user.getPassword()));
			repo.save(user);
		}
		return "Created";
	}
}
