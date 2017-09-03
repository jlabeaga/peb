package com.github.jlabeaga.peb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class PebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PebApplication.class, args);
	}
}
