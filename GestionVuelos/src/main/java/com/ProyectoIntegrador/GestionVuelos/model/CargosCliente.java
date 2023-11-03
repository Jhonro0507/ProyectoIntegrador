package com.ProyectoIntegrador.GestionVuelos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CargosCliente")

public class CargosCliente {

    private LocalDate fecha;
    private  String descripcion;
    private  int codOperaciones;
    private  double pvp;
    private  double difTarifa;
    private  double difTasas;
    private  double penCia;
    private  double penRMB;
    private  double costeOperacion;
    private  double tasas;

}