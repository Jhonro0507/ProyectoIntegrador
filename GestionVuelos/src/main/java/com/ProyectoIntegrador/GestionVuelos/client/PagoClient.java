package com.ProyectoIntegrador.GestionVuelos.client;


import com.ProyectoIntegrador.GestionVuelos.DTO.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Pagos", url = "http://localhost:8082/pagos")
@Component
public interface PagoClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PagoDTO> CrearPago(@RequestBody PagoDTO pagoDTO);

}