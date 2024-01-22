package com.app.splitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SplitterApplication {


	public static void main(String[] args) {
		SpringApplication.run(SplitterApplication.class, args);
	}


	@GetMapping
	public String home(){
		return "Hello";
	}
}
