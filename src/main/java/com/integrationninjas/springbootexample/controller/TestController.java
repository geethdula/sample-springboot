package com.integrationninjas.springbootexample.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping
	public Object hello() {
		Map<String, String> object = new HashMap<>();
		object.put("my name", "Geeth Dulanjana CICD done");
		object.put("email address", "geethdula1@gmail.com");
		return object;
	}

}
