package com.ProyectoIntegrador.GestionVuelos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private long id;
    private UUID reservaId;
    private Double totalPagar;
    private boolean pagada;
    private String metodoPago;
    private LocalDate fechaPago;
    private LocalTime horaPago;

}
