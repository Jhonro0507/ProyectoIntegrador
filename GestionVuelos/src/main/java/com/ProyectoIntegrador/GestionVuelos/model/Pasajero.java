package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pasajeros")
public class Pasajero {
    private String codPas;
    private String dni;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String tipoPasajero;
    private String billete;
    private double tasas;
    private String cg;
    private String ge;
    private double difTarifaNacional;

}
