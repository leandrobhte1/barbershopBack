package com.puc.barbershop;

import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.User;
import com.puc.barbershop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BarbershopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbershopApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Leandro", "Oliveira", "leandrobhte", "1234", new ArrayList<>(),null, null));
			userService.saveUser(new User(null, "Leandro", "Oliveira", "leandrobhte1", "1234", new ArrayList<>(), null, null));

			userService.addRoleToUser("leandrobhte", "ROLE_USER");
			userService.addRoleToUser("leandrobhte", "ROLE_MANAGER");
			userService.addRoleToUser("leandrobhte", "ROLE_ADMIN");
			userService.addRoleToUser("leandrobhte", "ROLE_SUPER_ADMIN");

			userService.addRoleToUser("leandrobhte1", "ROLE_USER");
			userService.addRoleToUser("leandrobhte1", "ROLE_MANAGER");
			userService.addRoleToUser("leandrobhte1", "ROLE_ADMIN");
			userService.addRoleToUser("leandrobhte1", "ROLE_SUPER_ADMIN");
		};
	}

}
