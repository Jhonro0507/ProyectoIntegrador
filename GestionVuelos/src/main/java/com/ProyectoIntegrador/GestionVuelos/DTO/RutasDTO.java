package com.ProyectoIntegrador.GestionVuelos.DTO;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RutasDTO {
    private List<Vuelo> vuelos = new ArrayList<>();
    private int escalas;

    public RutasDTO(RutasDTO otraRuta) {
        this.vuelos = new ArrayList<>(otraRuta.vuelos); // Copia la lista de vuelos
        this.escalas = otraRuta.escalas;
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
        actualizarEscalas();
    }

    private void actualizarEscalas() {
        escalas = vuelos.size() - 1;
    }
}
