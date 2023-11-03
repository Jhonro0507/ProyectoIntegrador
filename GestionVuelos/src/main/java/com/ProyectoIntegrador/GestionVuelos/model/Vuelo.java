package com.ProyectoIntegrador.GestionVuelos.model;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vuelos")
public class Vuelo {

    @Column(name = "numero", nullable = false, unique = true)
    private int numero;

    @Column(name = "tipo", nullable = false)
    @Pattern(regexp = "^(p|e|P|E)$", message = "El tipo de vuelo debe ser p: premium o e: estándar")
    private String tipo;

    @Column(name = "precio_base", nullable = false)
    private long precioBase;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idVuelo;

    @JsonIgnore
    @ManyToMany(mappedBy = "Vuelos", fetch = FetchType.EAGER)
    private List<Reserva> reservas  = new ArrayList<>();

    @PrePersist
    public void toLowerCase() {
        if (tipo != null) {
            tipo = tipo.toLowerCase(Locale.ROOT);
        }
    }
}


