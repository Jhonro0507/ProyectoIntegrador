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
@Table(name = "cliente")

public class Cliente {

    private String tipoIdentificacion;
    private String numIdentificacion;
    private String Login;
    private String Pasword;
    private char estado;
    private char permisos;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String correo;
}
