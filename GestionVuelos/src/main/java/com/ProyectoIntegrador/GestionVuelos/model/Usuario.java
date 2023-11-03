package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario {
    private String dni;
    private String login;
    private String password;
    private String estado;
    private String permisos;
    private String nombre;
    private String apellido;
    private long telefono;
    private String email;
}
