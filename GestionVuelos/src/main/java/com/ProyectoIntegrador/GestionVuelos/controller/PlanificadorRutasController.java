package com.ProyectoIntegrador.GestionVuelos.controller;

import com.ProyectoIntegrador.GestionVuelos.dto.RutasDTO;
import com.ProyectoIntegrador.GestionVuelos.service.PlanificadorRutasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/planificadorRutas")
public class PlanificadorRutasController {
    private final PlanificadorRutasService planificadorRutasService;
    @Autowired
    public PlanificadorRutasController(PlanificadorRutasService planificadorRutasService){
        this.planificadorRutasService = planificadorRutasService;
    }

    @PreAuthorize("permitAll")
    @GetMapping()
    public ResponseEntity<?> encontrarRutas(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam LocalDate fechaMinima
    ) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        if (fechaMinima.isBefore(LocalDate.now())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La fecha mínima de vuelo debe ser hoy");
        }
        LocalTime horaMinima = null;
        if (fechaMinima.isAfter(LocalDate.now())) {
            horaMinima = LocalTime.of(0, 0);
        } else if (fechaMinima.isEqual(LocalDate.now())) {
            horaMinima = LocalTime.now();
        }
        List<RutasDTO> rutas = planificadorRutasService.encontrarRutas(origen, destino,fechaMinima,horaMinima);
        if (rutas!=null){
            return ResponseEntity.ok(rutas);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron rutas entre "+origen+" y "+destino);
    }
}
