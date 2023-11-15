package com.ProyectoIntegrador.GestionVuelos;

import com.ProyectoIntegrador.GestionVuelos.model.Asiento;
import com.ProyectoIntegrador.GestionVuelos.repository.AsientoRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ClienteRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ReservaRepository;
import com.ProyectoIntegrador.GestionVuelos.service.AsientoService;
import com.ProyectoIntegrador.GestionVuelos.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AsientoServiceTest {

    private  AsientoRepository asientoRepository;
    private AsientoService asientoService;

    @BeforeEach
    public void setUp() {
        // mocks
       this.asientoRepository = mock(AsientoRepository.class);
       this.asientoService = new AsientoService(this.asientoRepository);
    }

    @Test
    public void testAsientosDisponiblesEnVueloSuccess() {
        // Arrange
        long vueloId = 123L;
        List<Asiento> asientosDisponibles = new ArrayList<>();
        // Add available seats to the list
        asientosDisponibles.add(new Asiento());

        // Mock the behavior of asientoRepository.asientosDisponiblesEnVuelo()
        when(asientoRepository.asientosDisponiblesEnVuelo(vueloId)).thenReturn(asientosDisponibles);

        // Act
        ResponseEntity<?> responseEntity = asientoService.asientosDisponiblesEnVuelo(vueloId);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(asientosDisponibles, responseEntity.getBody());
    }

    @Test
    public void testAsientosDisponiblesEnVueloNotFound() {
        // Arrange
        long vueloId = 456L;

        // Mock the behavior of asientoRepository.asientosDisponiblesEnVuelo() to return an empty list
        when(asientoRepository.asientosDisponiblesEnVuelo(vueloId)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> responseEntity = asientoService.asientosDisponiblesEnVuelo(vueloId);

        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("No hay asientos disponibles en el vuelo especificado.", responseEntity.getBody());
    }

    @Test
    public void testAsientosDisponiblesEnVueloError() {
        // Arrange
        long vueloId = 789L;

        // Mock the behavior of asientoRepository.asientosDisponiblesEnVuelo() to throw an exception
        when(asientoRepository.asientosDisponiblesEnVuelo(vueloId)).thenThrow(new RuntimeException("Some error occurred"));

        // Act
        ResponseEntity<?> responseEntity = asientoService.asientosDisponiblesEnVuelo(vueloId);

        // Assert
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Some error occurred", responseEntity.getBody());
    }

}
