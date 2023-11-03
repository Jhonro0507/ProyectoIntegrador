package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vuelos")
public class Vuelo {
    private LocalDate opFecha;
    private String opDes;
    private String codOp;
    private LocalDate fechaSalida;
    private String iOrigen;
    private String iDestino;
    private LocalDate iFechaLlegada;
    private String iNBillete;
    private String iAerolinea;
    private String nacional;
    private String proveedor;
    private String notas;
    private LocalDate dFechaSalida;
    private LocalDate dOrigen;
    private String dDestino;
    private LocalDate dFechaLlegada;
    private String dNBillete;
    private String dAerolinea;
}
