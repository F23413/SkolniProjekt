package com.kvadrazicdev.KvadRazic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class KvadRazicApplication {

	public static void main(String[] args) {
		SpringApplication.run(KvadRazicApplication.class, args);
	}

}
