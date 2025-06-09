package com.clotho.microservices;

import org.springframework.boot.SpringApplication;

public class TestProductApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
