package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.service.AsientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/asientos")
public class AsientoController {
    private final AsientoService asientoService;
    @Autowired
    public AsientoController(AsientoService asientoService){
        this.asientoService = asientoService;
    }

    @GetMapping("/{vueloId}")
    public ResponseEntity<?> asientosDisponiblesEnVuelo(@PathVariable long vueloId){
        return asientoService.asientosDisponiblesEnVuelo(vueloId);
    }

}
