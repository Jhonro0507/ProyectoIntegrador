package com.ProyectoIntegrador.GestionVuelos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String especie;
    @Column(nullable = false)
    private String raza;
    @Column(nullable = false)
    private int edad;
    @Column(nullable = false)
    private double peso;



    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "pasajero_tipo_documento"),
            @JoinColumn(name = "pasajero_numero_documento")
    })
    private Pasajero pasajero;
}
