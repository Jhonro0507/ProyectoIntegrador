package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "operacion")
public class Operacion {

    private Date fecha;
    private String descripcion;
    private int codOperacion;
    private String localizador;
    private String codCliente;
    private String codUsuario;
    private boolean activo;

}
