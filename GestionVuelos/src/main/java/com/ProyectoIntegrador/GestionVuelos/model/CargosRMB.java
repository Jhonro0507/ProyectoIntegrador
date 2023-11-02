package com.ProyectoIntegrador.GestionVuelos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CargosRMB")

public class CargosRMB {

    private LocalDate fecha;
    private  String descripcion;
    private  int codigoOperacion;
    private  double precioRMB;
    private  double CosteOperacion;

}
