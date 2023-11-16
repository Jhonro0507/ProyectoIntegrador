package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.service.VueloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/vuelos")
public class VueloController {

    private final VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }


    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<?> registrarVuelo(@RequestBody @Valid Vuelo vuelo) {
        return vueloService.registrarVuelo(vuelo);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<?> obtenerTodasLasVuelos() {
        return vueloService.obtenerTodasLasVuelos();
    }


    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{idVuelo}")
    public ResponseEntity<?> obtenerVueloPorId(@PathVariable Long idVuelo) {
        return vueloService.obtenerVueloPorId(idVuelo);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/{idVuelo}")
    public ResponseEntity<?> actualizarVuelo(@PathVariable Long idVuelo, @RequestBody @Valid Vuelo vuelo) {
        return vueloService.actualizarVuelo(idVuelo, vuelo);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/{idVuelo}")
    public ResponseEntity<?> eliminarVuelo(@PathVariable Long idVuelo) {
        return vueloService.eliminarVuelo(idVuelo);
    }


}

