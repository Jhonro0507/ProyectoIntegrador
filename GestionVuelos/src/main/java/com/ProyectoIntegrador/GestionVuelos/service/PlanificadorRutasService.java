package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.dto.RutasDTO;
import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;

import com.ProyectoIntegrador.GestionVuelos.repository.VueloRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanificadorRutasService {
    private final VueloRepository vueloRepository;

    @Autowired
    public PlanificadorRutasService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    public List<RutasDTO> encontrarRutas(String origen, String destino, LocalDate fechaMinima,LocalTime horaMinima) {
        List<RutasDTO> rutas = new ArrayList<>();
        buscarRutas(origen, destino, new RutasDTO(), rutas, fechaMinima,horaMinima);
        return rutas;
    }
    private void buscarRutas(String actual, String destino, RutasDTO rutaActual, List<RutasDTO> rutas, LocalDate fechaMinima,LocalTime horaMinima) {
        List<Vuelo> vuelos = vueloRepository.findVuelosDisponiblesPorOrigenYDestino(actual, destino,fechaMinima,horaMinima);

        if (actual.equals(destino)) {
            // Hemos llegado al destino, agregamos una copia de la rutaActual a la lista de rutas
            rutas.add(new RutasDTO(rutaActual)); // Asumiendo que RutasDTO tiene un constructor de copia
            return;
        }

        for (Vuelo vuelo : vuelos) {
            if (vuelo.getCiudadOrigen().equals(actual) && !rutaActual.getVuelos().contains(vuelo)) {
                rutaActual.agregarVuelo(vuelo);
                buscarRutas(vuelo.getCiudadDestino(), destino, rutaActual, rutas,vuelo.getFechaLlegada(),vuelo.getHoraLlegada());
                rutaActual.getVuelos().remove(vuelo);  // Elimina el vuelo no válido
            }
        }
    }

}