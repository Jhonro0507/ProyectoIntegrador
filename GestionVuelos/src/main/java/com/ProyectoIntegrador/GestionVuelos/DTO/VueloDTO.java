package com.ProyectoIntegrador.GestionVuelos.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VueloDTO {
    private long id;
    private String numeroVuelo;
    private String aerolinea;
    private String ciudadOrigen;
    private String ciudadDestino;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalDate fechaLlegada;
    private LocalTime horaLlegada;
}
