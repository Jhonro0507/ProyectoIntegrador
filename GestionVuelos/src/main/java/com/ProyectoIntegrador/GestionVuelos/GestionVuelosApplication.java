package com.ProyectoIntegrador.GestionVuelos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableFeignClients
@SpringBootApplication
public class GestionVuelosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionVuelosApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner createPasswordCommand(){
		return args -> {
			System.out.println(passwordEncoder.encode("clave123"));
			System.out.println(passwordEncoder.encode("clave456"));
		};
	}

}
