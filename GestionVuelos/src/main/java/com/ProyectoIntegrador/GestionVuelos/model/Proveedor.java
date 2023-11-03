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
@Table(name = "proveedores")
public class Proveedor {
    private String cif;
    private String nombreFiscal;
    private String direccion;
    private long telefono;
    private String email;
    private String banco;
    private String digOficina;
    private String digControl;
    private String ctaCte;
}
