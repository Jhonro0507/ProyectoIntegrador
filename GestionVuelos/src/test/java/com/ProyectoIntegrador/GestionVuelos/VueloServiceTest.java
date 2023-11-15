package com.ProyectoIntegrador.GestionVuelos;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.repository.VueloRepository;
import com.ProyectoIntegrador.GestionVuelos.service.VueloService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class VueloServiceTest {
    private VueloRepository vueloRepository;
    private VueloService vueloService;
    @BeforeEach
    public void setUp() {
        this.vueloRepository = mock(VueloRepository.class);
        this.vueloService = new VueloService(this.vueloRepository);
    }

    @Test
    public void testRegistrarVueloSuccess() {
        // Arrange
        Vuelo vuelo = new Vuelo(/* provide necessary vuelo data */);
        when(vueloRepository.save(vuelo)).thenReturn(vuelo);

        // Act
        ResponseEntity<?> responseEntity = vueloService.registrarVuelo(vuelo);

        // Assert
        verify(vueloRepository, times(1)).save(vuelo);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(vuelo, responseEntity.getBody());
    }

    @Test
    public void testRegistrarVueloFailure() {
        // Arrange
        Vuelo vuelo = new Vuelo(/* provide necessary vuelo data */);
        when(vueloRepository.save(vuelo)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = vueloService.registrarVuelo(vuelo);

        // Assert
        verify(vueloRepository, times(1)).save(vuelo);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerTodasLasVuelosExito() {
        // Arrange
        List<Vuelo> vuelos = Arrays.asList(new Vuelo(/* provide necessary vuelo data */));
        when(vueloRepository.findAll()).thenReturn(vuelos);

        // Act
        ResponseEntity<?> responseEntity = vueloService.obtenerTodasLasVuelos();

        // Assert
        verify(vueloRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(vuelos, responseEntity.getBody());
    }

    @Test
    public void testObtenerTodasLasVuelosNotFound() {
        // Arrange
        when(vueloRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> responseEntity = vueloService.obtenerTodasLasVuelos();

        // Assert
        verify(vueloRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("No hay vuelos", responseEntity.getBody());
    }

    @Test
    public void testObtenerTodasLasVuelosFailure() {
        // Arrange
        when(vueloRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = vueloService.obtenerTodasLasVuelos();

        // Assert
        verify(vueloRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerVueloPorIdSuccess() {
        // Arrange
        Long idVuelo = 1L;
        Vuelo vuelo = new Vuelo(/* provide necessary vuelo data */);
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.of(vuelo));

        // Act
        ResponseEntity<?> responseEntity = vueloService.obtenerVueloPorId(idVuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(vuelo, responseEntity.getBody());
    }

    @Test
    public void testObtenerVueloPorIdNotFound() {
        // Arrange
        Long idVuelo = 1L;
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = vueloService.obtenerVueloPorId(idVuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("Vuelo no encontrada", responseEntity.getBody());
    }

    @Test
    public void testObtenerVueloPorIdFailure() {
        // Arrange
        Long idVuelo = 1L;
        when(vueloRepository.findById(idVuelo)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = vueloService.obtenerVueloPorId(idVuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testActualizarVueloSuccess() {
        // Arrange
        Long idVuelo = 1L;
        Vuelo vuelo = new Vuelo(/* provide necessary vuelo data */);
        Vuelo vueloExistente = new Vuelo(/* provide necessary existing vuelo data */);
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.of(vueloExistente));
        when(vueloRepository.save(vueloExistente)).thenReturn(vueloExistente);

        // Act
        ResponseEntity<?> responseEntity = vueloService.actualizarVuelo(idVuelo, vuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        verify(vueloRepository, times(1)).save(vueloExistente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(vueloExistente, responseEntity.getBody());
    }

    @Test
    public void testActualizarVueloNotFound() {
        // Arrange
        Long idVuelo = 1L;
        Vuelo vuelo = new Vuelo(/* provide necessary vuelo data */);
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = vueloService.actualizarVuelo(idVuelo, vuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        verify(vueloRepository, never()).save(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("Vuelo no encontrada", responseEntity.getBody());
    }

    @Test
    public void testActualizarVueloFailure() {
        // Arrange
        Long idVuelo = 1L;
        Vuelo vuelo = new Vuelo(/* provide necessary vuelo data */);
        Vuelo vueloExistente = new Vuelo(/* provide necessary existing vuelo data */);
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.of(vueloExistente));
        when(vueloRepository.save(vueloExistente)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = vueloService.actualizarVuelo(idVuelo, vuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        verify(vueloRepository, times(1)).save(vueloExistente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testEliminarVueloSuccess() {
        // Arrange
        Long idVuelo = 1L;
        Vuelo vueloExistente = new Vuelo(/* provide necessary existing vuelo data */);
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.of(vueloExistente));

        // Act
        ResponseEntity<?> responseEntity = vueloService.eliminarVuelo(idVuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        verify(vueloRepository, times(1)).delete(vueloExistente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Vuelo eliminada con éxito", responseEntity.getBody());
    }

    @Test
    public void testEliminarVueloNotFound() {
        // Arrange
        Long idVuelo = 1L;
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = vueloService.eliminarVuelo(idVuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        verify(vueloRepository, never()).delete(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("Vuelo no encontrada", responseEntity.getBody());
    }

    @Test
    public void testEliminarVueloFailure() {
        // Arrange
        Long idVuelo = 1L;
        Vuelo vueloExistente = new Vuelo(/* provide necessary existing vuelo data */);
        when(vueloRepository.findById(idVuelo)).thenReturn(Optional.of(vueloExistente));
        doThrow(new RuntimeException("Test Exception")).when(vueloRepository).delete(vueloExistente);

        // Act
        ResponseEntity<?> responseEntity = vueloService.eliminarVuelo(idVuelo);

        // Assert
        verify(vueloRepository, times(1)).findById(idVuelo);
        verify(vueloRepository, times(1)).delete(vueloExistente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }
}
