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
@Table(name = "Aerolinea")


public class Aerolinea {

    private int numero;
    private String nombre;
    private long telefono;
    private String email;
}
