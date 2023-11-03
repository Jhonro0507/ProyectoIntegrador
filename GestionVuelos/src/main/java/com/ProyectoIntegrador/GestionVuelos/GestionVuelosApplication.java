package com.ProyectoIntegrador.GestionVuelos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ProyectoIntegrador.GestionVuelos.repository")
public class GestionVuelosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionVuelosApplication.class, args);
	}

}
