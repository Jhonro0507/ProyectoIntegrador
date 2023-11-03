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
@Table(name = "pagosProveedor")
public class PagosProveedor {

    private int indice;
    private Date opFecha;
    private String opDes;
    private String opCodepe;
    private String localizador;
    private Date fechaEmision;
    private String proveedor;
    private double totalProveedor;
    private Date fecha;
    private double importe;
    private String entidad;
    private double saldo;
}
