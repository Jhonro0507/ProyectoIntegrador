package com.ProyectoIntegrador.GestionVuelos.model;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vuelos")
public class Vuelo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String numeroVuelo;
    @Column(nullable = false)
    private String aerolinea;
    @Column(nullable = false)
    private String ciudadOrigen;
    @Column(nullable = false)
    private String ciudadDestino;
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate fechaSalida;
    @Column(columnDefinition = "TIME", nullable = false)
    private LocalTime horaSalida;
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate fechaLlegada;
    @Column(columnDefinition = "TIME", nullable = false)
    private LocalTime horaLlegada;
    @Column(nullable = false)
    private double precio;
    @Column(nullable = false)
    private int asientosDisponibles;
    @Column(nullable = false)
    private int asientosTotales;



    @JsonIgnore
    @ManyToMany(mappedBy = "vuelos", fetch = FetchType.EAGER)
    private List<Reserva> reservas  = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "vuelo", fetch = FetchType.EAGER)
    private List<Asiento> asientos = new ArrayList<>();
}


