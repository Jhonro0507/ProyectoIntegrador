package com.ProyectoIntegrador.GestionVuelos.DTO;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
public class ReservaDTO {
    private UUID id;
    private LocalDate fecha;
    private LocalTime hora;
    private double totalPagar;
    private List<String> numerosVuelos;
}