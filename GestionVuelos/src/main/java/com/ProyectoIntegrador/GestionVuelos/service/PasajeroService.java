package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.repository.PasajeroRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasajeroService {
    private final PasajeroRepository pasajeroRepository;
    private final ReservaRepository reservaRepository;

    @Autowired
    public PasajeroService(PasajeroRepository pasajeroRepository, ReservaRepository reservaRepository) {
        this.pasajeroRepository = pasajeroRepository;
        this.reservaRepository = reservaRepository;
    }

    public ResponseEntity<?> registrarPasajero(Pasajero pasajero) {
        try {
            Pasajero pasajeroRegistrado = pasajeroRepository.save(pasajero);
            return ResponseEntity.status(HttpStatus.CREATED).body(pasajeroRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerPasajeroPorId(Long idPasajero) {
        try {
            Pasajero pasajero = pasajeroRepository.findById(idPasajero).orElse(null);

            if (pasajero != null) {
                return ResponseEntity.status(HttpStatus.OK).body(pasajero);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El pasajero con id " + idPasajero + " no se encontró");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerTodosLosPasajeros() {
        try {
            List<Pasajero> pasajeros = pasajeroRepository.findAll();

            if (!pasajeros.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(pasajeros);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron pasajeros");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> actualizarPasajero(Long id, Pasajero pasajeroActualizado) {
        try {
            Pasajero pasajeroExistente = pasajeroRepository.findById(id).orElse(null);

            if (pasajeroExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Pasajero con id " + id + " no se encontró");
            }

            pasajeroExistente.setNombre(pasajeroActualizado.getNombre());
            pasajeroExistente.setApellido(pasajeroActualizado.getApellido());
            pasajeroExistente.setCedula(pasajeroActualizado.getCedula());
            pasajeroExistente.setDireccion(pasajeroActualizado.getDireccion());
            pasajeroExistente.setEdad(pasajeroActualizado.getEdad());
            pasajeroExistente.setCorreoElectronico(pasajeroActualizado.getCorreoElectronico());

            pasajeroRepository.save(pasajeroExistente);

            return ResponseEntity.status(HttpStatus.OK).body(pasajeroExistente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> eliminarPasajero(Long id) {
        try {
            Pasajero pasajeroExistente = pasajeroRepository.findById(id).orElse(null);

            if (pasajeroExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Pasajero con id " + id + " no se encontró");
            }

            pasajeroRepository.delete(pasajeroExistente);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pasajero eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerReservasPorCedula(String cedula) {
        try {
            List<Reserva> reservas = reservaRepository.obtenerReservasPorCedula(cedula);// Implementa este método en tu repositorio ReservaRepository
            return ResponseEntity.status(HttpStatus.OK).body(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}


