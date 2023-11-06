package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import com.ProyectoIntegrador.GestionVuelos.service.PasajeroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pasajeros")
public class PasajeroController {

    private final PasajeroService pasajeroService;

    @Autowired
    public PasajeroController(PasajeroService pasajeroService) {
        this.pasajeroService = pasajeroService;
    }

    @PostMapping
    public ResponseEntity<?> registrarPasajero(@RequestBody @Valid Pasajero pasajero) {
        return pasajeroService.registrarPasajero(pasajero);
    }

    @GetMapping("/{idPasajero}")
    public ResponseEntity<?> obtenerPasajeroPorId(@PathVariable Long idPasajero) {
        return pasajeroService.obtenerPasajeroPorId(idPasajero);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosPasajeros() {
        return pasajeroService.obtenerTodosLosPasajeros();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPasajero(@PathVariable Long id, @RequestBody Pasajero pasajeroActualizado) {
        return pasajeroService.actualizarPasajero(id, pasajeroActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPasajero(@PathVariable Long id) {
        return pasajeroService.eliminarPasajero(id);
    }

    @GetMapping("/{cedula}/reservas")
    public ResponseEntity<?> obtenerReservasPorIdPasajero(@PathVariable IdPasajero idPasajero) {
        return pasajeroService.obtenerReservasPorIdPasajero(idPasajero);
    }
}

