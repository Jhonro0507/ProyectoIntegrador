package com.ProyectoIntegrador.GestionVuelos.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
