package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.DTO.PagoDTO;
import com.ProyectoIntegrador.GestionVuelos.DTO.RespuestaCreacionPagoDTO;
import com.ProyectoIntegrador.GestionVuelos.client.PagoClient;
import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final PagoClient pagoClient;

    @Autowired
    public ReservaController(ReservaService reservaService, PagoClient pagoClient) {
        this.reservaService = reservaService;
        this.pagoClient = pagoClient;
    }

    @PostMapping
    public ResponseEntity<?> registrarReserva(@RequestBody Reserva reserva) {


        Reserva reservaRegistrada = (Reserva) reservaService.registrarReserva(reserva).getBody();

        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setReservaId(reservaRegistrada.getId());
        pagoDTO.setTotalPagar(reservaRegistrada.getTotalPagar());
        pagoDTO.setPagada(reservaRegistrada.getPagada());

        PagoDTO pagoCreado = (PagoDTO) pagoClient.CrearPago(pagoDTO).getBody();

        RespuestaCreacionPagoDTO respuesta = new RespuestaCreacionPagoDTO(pagoCreado, reservaRegistrada);


        return ResponseEntity.ok(respuesta);
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
    public ResponseEntity<?> actualizarReserva(@PathVariable UUID id, @RequestBody Reserva reservaActualizada) {
        return reservaService.actualizarReserva(id, reservaActualizada);
    }

    @PutMapping("/pago")
    public ResponseEntity<?> pagarReserva(@RequestBody PagoDTO pagoDTO) {
        return reservaService.pagarReserva(pagoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        return reservaService.eliminarReserva(id);
    }

}
