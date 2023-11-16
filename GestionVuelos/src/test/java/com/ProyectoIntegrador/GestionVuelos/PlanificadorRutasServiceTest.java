package com.ProyectoIntegrador.GestionVuelos;

import com.ProyectoIntegrador.GestionVuelos.dto.RutasDTO;
import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.repository.VueloRepository;
import com.ProyectoIntegrador.GestionVuelos.service.PlanificadorRutasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlanificadorRutasServiceTest {
    private VueloRepository vueloRepository;
    private PlanificadorRutasService planificadorRutasService;

    @BeforeEach
    public void setUp() {
        this.vueloRepository = mock(VueloRepository.class);
        this.planificadorRutasService = new PlanificadorRutasService(this.vueloRepository);
    }

    @Test
    public void testEncontrarRutasExito() {
        // Arrange
        String origen = "CiudadA";
        String destino = "CiudadB";
        LocalDate fechaMinima = LocalDate.now();
        LocalTime horaMinima = LocalTime.of(8, 0);

        // Mock vuelos data
        List<Vuelo> vuelos = new ArrayList<>();
        Vuelo vuelo = new Vuelo();
        vuelo.setCiudadOrigen("Marruecos");
        vuelo.setCiudadDestino("Bogota");
        vuelo.setHoraLlegada(horaMinima);
        vuelo.setFechaLlegada(fechaMinima);
        Vuelo vuelo2 = new Vuelo();
        vuelo2.setCiudadOrigen(origen);
        vuelo2.setCiudadDestino(origen);
        vuelo2.setHoraLlegada(horaMinima);
        vuelo2.setFechaLlegada(fechaMinima);
        vuelos.add(vuelo);
        vuelos.add(vuelo2);
        when(vueloRepository.findVuelosDisponiblesPorOrigenYDestino(eq(origen), eq(destino), eq(fechaMinima), eq(horaMinima)))
                .thenReturn(vuelos);

        // Act
        List<RutasDTO> rutas = planificadorRutasService.encontrarRutas(origen, destino, fechaMinima, horaMinima);

        // Assert
        verify(vueloRepository, times(2)).findVuelosDisponiblesPorOrigenYDestino(eq(origen), eq(destino), eq(fechaMinima), eq(horaMinima));

        // Perform additional assertions based on the actual implementation of the method
    }

    @Test
    public void testEncontrarRutasNoResultados() {
        // Arrange
        String origen = "CiudadA";
        String destino = "CiudadA";
        LocalDate fechaMinima = LocalDate.now();
        LocalTime horaMinima = LocalTime.of(8, 0);

        // Mock no vuelos data
        when(vueloRepository.findVuelosDisponiblesPorOrigenYDestino(eq(origen), eq(destino), eq(fechaMinima), eq(horaMinima)))
                .thenReturn(new ArrayList<>());

        // Act
        List<RutasDTO> rutas = planificadorRutasService.encontrarRutas(origen, destino, fechaMinima, horaMinima);

        // Assert
        verify(vueloRepository, times(1)).findVuelosDisponiblesPorOrigenYDestino(eq(origen), eq(destino), eq(fechaMinima), eq(horaMinima));
        // Perform additional assertions based on the actual implementation of the method
    }
}
