package com.ProyectoIntegrador.Pagos.client;

import com.ProyectoIntegrador.Pagos.model.Pago;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "GestionVuelos", url = "http://localhost:8080")
@Component
public interface GestionVuelosClient {

    @PutMapping(value = "/reservas/pago", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Pago> CrearPago(@RequestBody Pago pago);

}

