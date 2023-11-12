package com.ProyectoIntegrador.GestionVuelos;

import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.repository.ClienteRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.PasajeroRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ReservaRepository;
import com.ProyectoIntegrador.GestionVuelos.service.ClienteService;
import com.ProyectoIntegrador.GestionVuelos.service.PasajeroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PasajeroServiceTest {

    private PasajeroRepository pasajeroRepository;
    private ReservaRepository reservaRepository;
    private PasajeroService pasajeroService;
    @BeforeEach
    public void setUp() {
        // mocks
        this.pasajeroRepository = mock(PasajeroRepository.class);
        this.reservaRepository = mock(ReservaRepository.class);
        this.pasajeroService = new PasajeroService(this.pasajeroRepository, this.reservaRepository);
    }


    @Test
    public void testRegistrarPasajeroExito() {
        // Arrange
        Pasajero pasajero = new Pasajero();
        when(pasajeroRepository.save(pasajero)).thenReturn(pasajero);

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.registrarPasajero(pasajero);

        // Assert
        verify(pasajeroRepository, times(1)).save(pasajero);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(pasajero, responseEntity.getBody());
    }

    @Test
    public void testRegistrarPasajeroFallo() {
        // Arrange
        Pasajero pasajero = new Pasajero();
        when(pasajeroRepository.save(pasajero)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.registrarPasajero(pasajero);

        // Assert
        verify(pasajeroRepository, times(1)).save(pasajero);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerPasajeroPorIdExito() {
        // Arrange
        Long idPasajero = 1L;
        Pasajero pasajero = new Pasajero();
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.of(pasajero));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerPasajeroPorId(idPasajero);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(pasajero, responseEntity.getBody());
    }

    @Test
    public void testObtenerPasajeroPorIdNoEncontrado() {
        // Arrange
        Long idPasajero = 1L;
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerPasajeroPorId(idPasajero);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("El pasajero con id " + idPasajero + " no se encontró", responseEntity.getBody());
    }

    @Test
    public void testObtenerPasajeroPorIdFallo() {
        // Arrange
        Long idPasajero = 1L;
        when(pasajeroRepository.findById(idPasajero)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerPasajeroPorId(idPasajero);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosPasajerosExito() {
        // Arrange
        List<Pasajero> pasajeros = Arrays.asList(new Pasajero());
        when(pasajeroRepository.findAll()).thenReturn(pasajeros);

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerTodosLosPasajeros();

        // Assert
        verify(pasajeroRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(pasajeros, responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosPasajerosNoEncontrado() {
        // Arrange
        when(pasajeroRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerTodosLosPasajeros();

        // Assert
        verify(pasajeroRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("No se encontraron pasajeros", responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosPasajerosFallo() {
        // Arrange
        when(pasajeroRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerTodosLosPasajeros();

        // Assert
        verify(pasajeroRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testActualizarPasajeroExito() {
        // Arrange
        Long idPasajero = 1L;
        Pasajero pasajeroExistente = new Pasajero();
        Pasajero pasajeroActualizado = new Pasajero();
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.of(pasajeroExistente));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.actualizarPasajero(idPasajero, pasajeroActualizado);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        verify(pasajeroRepository, times(1)).save(pasajeroExistente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(pasajeroExistente, responseEntity.getBody());
    }

    @Test
    public void testActualizarPasajeroNoEncontrado() {
        // Arrange
        Long idPasajero = 1L;
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.actualizarPasajero(idPasajero, new Pasajero());

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        verify(pasajeroRepository, never()).save(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("El Pasajero con id " + idPasajero + " no se encontró", responseEntity.getBody());
    }

    @Test
    public void testActualizarPasajeroFallo() {
        // Arrange
        Long idPasajero = 1L;
        Pasajero pasajeroExistente = new Pasajero();
        Pasajero pasajeroActualizado = new Pasajero();
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.of(pasajeroExistente));
        when(pasajeroRepository.save(pasajeroExistente)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.actualizarPasajero(idPasajero, pasajeroActualizado);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        verify(pasajeroRepository, times(1)).save(pasajeroExistente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testEliminarPasajeroExito() {
        // Arrange
        Long idPasajero = 1L;
        Pasajero pasajeroExistente = new Pasajero();
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.of(pasajeroExistente));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.eliminarPasajero(idPasajero);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        verify(pasajeroRepository, times(1)).delete(pasajeroExistente);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assertions.assertEquals("Pasajero eliminado con éxito", responseEntity.getBody());
    }

    @Test
    public void testEliminarPasajeroNoEncontrado() {
        // Arrange
        Long idPasajero = 1L;
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.eliminarPasajero(idPasajero);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        verify(pasajeroRepository, never()).delete(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("El Pasajero con id " + idPasajero + " no se encontró", responseEntity.getBody());
    }

    @Test
    public void testEliminarPasajeroFallo() {
        // Arrange
        Long idPasajero = 1L;
        Pasajero pasajeroExistente = new Pasajero();
        when(pasajeroRepository.findById(idPasajero)).thenReturn(Optional.of(pasajeroExistente));
        doThrow(new RuntimeException("Test Exception")).when(pasajeroRepository).delete(pasajeroExistente);

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.eliminarPasajero(idPasajero);

        // Assert
        verify(pasajeroRepository, times(1)).findById(idPasajero);
        verify(pasajeroRepository, times(1)).delete(pasajeroExistente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerReservasPorIdPasajeroSuccess() {
        // Arrange
        IdPasajero idPasajero = new IdPasajero();
        List<Reserva> reservas = Arrays.asList(new Reserva());
        when(reservaRepository.obtenerReservasPorIdPasajero(idPasajero)).thenReturn(reservas);

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerReservasPorIdPasajero(idPasajero);

        // Assert
        verify(reservaRepository, times(1)).obtenerReservasPorIdPasajero(idPasajero);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(reservas, responseEntity.getBody());
    }

    @Test
    public void testObtenerReservasPorIdPasajeroFailure() {
        // Arrange
        IdPasajero idPasajero = new IdPasajero();
        when(reservaRepository.obtenerReservasPorIdPasajero(idPasajero)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = pasajeroService.obtenerReservasPorIdPasajero(idPasajero);

        // Assert
        verify(reservaRepository, times(1)).obtenerReservasPorIdPasajero(idPasajero);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }
}
