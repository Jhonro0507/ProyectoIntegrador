package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

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
    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public List<Asiento> getAllAsientos() {
        return null;
    }

    public Asiento getAsientoById(Long id) {
        return null;
    }

    public Asiento createAsiento(Asiento asiento) {
        return asiento;
    }

    public Asiento updateAsiento(Long id, Asiento asiento) {
        return asiento;
    }

    public void deleteAsiento(Long id) {
    }
}


