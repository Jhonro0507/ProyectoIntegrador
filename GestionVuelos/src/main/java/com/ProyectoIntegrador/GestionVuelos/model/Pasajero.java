package com.ProyectoIntegrador.GestionVuelos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pasajeros")
public class Pasajero {

    @EmbeddedId
    private IdPasajero id;
    @Column(nullable = false)
    private String nombre;
    @NotNull
    @Column
    private String apellido1;
    @Column
    private String apellido2;
    @Column
    private String direccion;
    @Min(0)
    @Column(nullable = false)
    private int edad;
    @Email
    @Column(nullable = false)
    private String email;



    @JsonManagedReference
    @OneToMany(mappedBy = "pasajero", fetch = FetchType.EAGER)
    private List<Mascota> mascotas = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "pasajero", fetch = FetchType.EAGER)
    private List<Equipaje> equipajes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "pasajeros", fetch = FetchType.EAGER)
    private List<Reserva> reservas  = new ArrayList<>();

}
