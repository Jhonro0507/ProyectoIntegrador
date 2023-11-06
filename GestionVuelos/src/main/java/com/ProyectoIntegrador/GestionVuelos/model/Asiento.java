package com.ProyectoIntegrador.GestionVuelos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "asientos")
public class Asiento {

    @EmbeddedId
    private IdAsiento id;
    @Column(nullable = false)
    @Pattern(regexp = "^(E|EP|B|P)$", message = "La clase de los asientos debe ser económica (E), económica premium (EP), business (B) o primera clase (P).")
    private String clase;
    @Column(nullable = false)
    @Pattern(regexp = "^(ocupado|reservado|disponible)$", message = "El estado del asiento solo puede ser ocupado, reservado o disponible")
    private String estado;
    @Column
    private String adicional;



    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

}


