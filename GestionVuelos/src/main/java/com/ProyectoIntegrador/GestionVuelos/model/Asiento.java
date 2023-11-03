package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "asientos")
public class Asiento {
    @Id
    @Pattern(regexp = "^(E|EP|B|P)$", message = "La clase de los asientos debe ser económica (E), económica premium (EP), business (B) o primera clase (P).")
    private String clase;

    @Pattern(regexp = "\\d+", message = "La fila debe ser numérica")
    @Id
    private String fila;

    @Pattern(regexp = "[A-Z]", message = "La columna debe ser una única letra mayúscula")
    @Id
    private String columna;


}
