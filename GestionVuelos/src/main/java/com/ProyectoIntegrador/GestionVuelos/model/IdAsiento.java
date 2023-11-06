package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IdAsiento implements Serializable {

    @Column(name = "asiento_fila")
    @Pattern(regexp = "\\d+", message = "La fila debe ser numérica")
    private String fila;

    @Column(name = "asiento_columna")
    @Pattern(regexp = "[A-Z]", message = "La columna debe ser una única letra mayúscula")
    private String columna;

}
