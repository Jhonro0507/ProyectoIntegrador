package com.ProyectoIntegrador.GestionVuelos.dto;
public class AsientoDTO {
    private String clase;
    private String fila;
    private String columna;

    public AsientoDTO() {
        // Constructor vacío
    }

    public AsientoDTO(String clase, String fila, String columna) {
        this.clase = clase;
        this.fila = fila;
        this.columna = columna;
    }

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
}

