package com.ProyectoIntegrador.GestionVuelos.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipajes")
public class Equipaje {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column
    private String descripcion;
    @Column(nullable = false)
    private double peso;
    @Column(nullable = false)
    private boolean bodega;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "pasajero_tipo_documento", referencedColumnName = "pasajero_tipo_documento"),
            @JoinColumn(name = "pasajero_numero_documento", referencedColumnName = "pasajero_numero_documento")
    })
    private Pasajero pasajero;

}
