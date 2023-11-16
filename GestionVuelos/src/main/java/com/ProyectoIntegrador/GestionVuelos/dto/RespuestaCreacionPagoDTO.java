package com.ProyectoIntegrador.GestionVuelos.dto;

import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaCreacionPagoDTO {
    private PagoDTO pagoDTO;
    private Reserva reserva;
}
