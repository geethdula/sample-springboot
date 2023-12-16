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
		object.put("MyCICD Demo", "Geeth Dulanjana - Sample Spring boot app");
		object.put("emailaddress", "geethdula1@gmail.com");
		return object;
	}
	@GetMapping("/user")
	public Object hello2() {
		Map<String, String> object = new HashMap<>();
		object.put("This is CICD", "Jenkins CICD to deply to ECS");
		object.put("emailaddress", "geethdula1@gmail.com");
		return object;
	}

}
