package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @Pattern(regexp = "\\d+", message = "La cédula debe ser numérica")
    private String numeroDocumento;

    @Id
    @Pattern(regexp = "^(CC|TI|RC|CE|CI|DNI)$", message = "Tipo de documento de identidad inválido")
    private String tipoDocumento;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "primerApellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundoApellido", nullable = false)
    private String segundoApellido;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefonoPrincipal", nullable = false)
    private long telefonoPrincipal;

    @Column(name = "telefonoSecundario")
    private long telefonoSecundario;

    @Column(name = "observaciones")
    private String observaciones;

}
