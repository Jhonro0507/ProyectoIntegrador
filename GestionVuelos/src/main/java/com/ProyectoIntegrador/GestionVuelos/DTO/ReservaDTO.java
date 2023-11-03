package com.ProyectoIntegrador.GestionVuelos.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ReservaDTO {
    private UUID idReserva;
    private LocalDate fechaReserva;
    private long totalPagar;
    private List<Integer> numerosVuelos;
}