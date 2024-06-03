package com.practice.practice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@GetMapping("/index")
	public String getIndex() {
		return "Index Controller";
	}
}
