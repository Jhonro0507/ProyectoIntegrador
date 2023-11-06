package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column
    private String direccion;
    @Column(nullable = false)
    private String telefono;
    @Column
    private String imagenPerfil;
    @Column(nullable = false)
    private Boolean administrador;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cliente_pasajero",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "pasajero_tipo_documento"),
                    @JoinColumn(name = "pasajero_numero_documento")
            },
            uniqueConstraints = {
                    @UniqueConstraint(name = "unique_cliente_pasajero", columnNames = {"cliente_id","pasajero_tipo_documento", "pasajero_numero_documento"})
            }
    )
    private List<Pasajero> pasajeros = new ArrayList<>();
}
