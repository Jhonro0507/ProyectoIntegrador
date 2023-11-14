package com.ProyectoIntegrador.GestionVuelos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
public class GestionVuelosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionVuelosApplication.class, args);
	}

}
