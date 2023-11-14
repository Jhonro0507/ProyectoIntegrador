package com.ProyectoIntegrador.GestionVuelos.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IdPasajero implements Serializable {

    @Column(name = "pasajero_tipo_documento")
    @Pattern(regexp = "^(CC|TI|RC|CE|CI|DNI)$", message = "El tipo de documento debe ser CC,TI,RC,CE,CI,DNI")
    private String tipoDocumento;

    @Column(name = "pasajero_numero_documento")
    @Pattern(regexp = "\\d+", message = "El número del documento solo puede contener dígitos numéricos")
    private String numeroDocumento;
}
