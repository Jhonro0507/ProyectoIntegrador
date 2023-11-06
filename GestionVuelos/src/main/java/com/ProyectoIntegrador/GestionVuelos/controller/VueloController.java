package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.service.VueloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> registrarVuelo(@RequestBody @Valid Vuelo vuelo) {
        return vueloService.registrarVuelo(vuelo);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasVuelos() {
        return vueloService.obtenerTodasLasVuelos();
    }

    @GetMapping("/{idVuelo}")
    public ResponseEntity<?> obtenerVueloPorId(@PathVariable Long idVuelo) {
        return vueloService.obtenerVueloPorId(idVuelo);
    }

    @PutMapping("/{idVuelo}")
    public ResponseEntity<?> actualizarVuelo(@PathVariable Long idVuelo, @RequestBody @Valid Vuelo vuelo) {
        return vueloService.actualizarVuelo(idVuelo, vuelo);
    }

    @DeleteMapping("/{idVuelo}")
    public ResponseEntity<?> eliminarVuelo(@PathVariable Long idVuelo) {
        return vueloService.eliminarVuelo(idVuelo);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> obtenerVuelosDisponiblesPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return vueloService.obtenerVuelosDisponiblesPorFecha(fecha);
    }

    @GetMapping("/disponibles/filtradas")
    public ResponseEntity<?> obtenerVuelosDisponiblesPorFechaYTipo(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(value = "tipo", required = false) String tipo) {
        return vueloService.obtenerVuelosDisponiblesPorFechaYTipo(fecha, tipo);
    }
}

