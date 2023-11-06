package com.ProyectoIntegrador.GestionVuelos.model;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    private UUID id;
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate fecha;
    @Column(columnDefinition = "TIME", nullable = false)
    private LocalTime hora;
    @Column(nullable = false)
    private double totalPagar;
    @Column(nullable = false)
    @Pattern(regexp = "^(pendiente|confirmada|cancelada|modificada)$")
    private String estado;
    @Column(nullable = false)
    private boolean pagada;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "reserva_pasajero",
            joinColumns = {
                    @JoinColumn(name = "reserva_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "pasajero_tipo_documento"),
                    @JoinColumn(name = "pasajero_numero_documento")
            },
            uniqueConstraints = {
                    @UniqueConstraint(name = "unique_reserva_pasajero", columnNames = {"reserva_id", "pasajero_tipo_documento", "pasajero_numero_documento"})
            }
    )
    private List<Pasajero> pasajeros = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "reserva_vuelo",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "vuelo_id"),
            uniqueConstraints = {
                    @UniqueConstraint(name = "unique_reserva_vuelo", columnNames = {"reserva_id", "vuelo_id"})
            }
    )
    private List<Vuelo> vuelos = new ArrayList<>();

}
