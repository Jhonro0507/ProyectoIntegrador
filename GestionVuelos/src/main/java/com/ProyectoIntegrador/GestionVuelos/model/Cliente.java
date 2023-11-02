package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @Pattern(regexp = "\\d+", message = "La cédula debe ser numérica")
    private String numeroDocumento;
    @Id
    private String tipoDocumento;
}
