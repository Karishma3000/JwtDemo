package com.example.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableMethodSecurity
public class UserServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
