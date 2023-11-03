package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<?> registrarReserva(@RequestBody @Valid Reserva reserva) {
        return reservaService.registrarReserva(reserva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable UUID id) {
        return reservaService.obtenerReservaPorId(id);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasReservas() {
        return reservaService.obtenerTodasLasReservas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable UUID id, @RequestBody @Valid Reserva reservaActualizada) {
        return reservaService.actualizarReserva(id, reservaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        return reservaService.eliminarReserva(id);
    }
}
