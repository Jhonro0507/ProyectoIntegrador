package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VueloService {

    private final VueloRepository vueloRepository;

    @Autowired
    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    public ResponseEntity<?> registrarVuelo(Vuelo vuelo) {
        try {
            Vuelo vueloRegistrada = vueloRepository.save(vuelo);
            return ResponseEntity.status(HttpStatus.CREATED).body(vueloRegistrada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerTodasLasVuelos() {
        try {
            List<Vuelo> Vuelos = vueloRepository.findAll();

            if (!Vuelos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(Vuelos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han agregado Vuelos");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerVueloPorId(Long idVuelo) {
        try {
            Vuelo vuelo = vueloRepository.findById(idVuelo).orElse(null);

            if (vuelo != null) {
                return ResponseEntity.status(HttpStatus.OK).body(vuelo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vuelo no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> actualizarVuelo(Long idVuelo, Vuelo vuelo) {
        try {
            Vuelo vueloExistente = vueloRepository.findById(idVuelo).orElse(null);

            if (vueloExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vuelo no encontrada");
            }
            vueloExistente.setNumeroVuelo(vuelo.getNumeroVuelo());
            vueloExistente.setAerolinea(vuelo.getAerolinea());
            vueloExistente.setCiudadOrigen(vuelo.getCiudadOrigen());
            vueloExistente.setCiudadDestino(vuelo.getCiudadDestino());
            vueloExistente.setHoraSalida(vuelo.getHoraSalida());
            vueloExistente.setHoraLlegada(vuelo.getHoraLlegada());
            vueloExistente.setPrecio(vuelo.getPrecio());
            vueloExistente.setAsientosDisponibles(vuelo.getAsientosDisponibles());
            vueloExistente.setAsientosTotales(vuelo.getAsientosTotales());

            Vuelo vueloActualizada = vueloRepository.save(vueloExistente);
            return ResponseEntity.status(HttpStatus.OK).body(vueloActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> eliminarVuelo(Long idVuelo) {
        try {
            Vuelo vueloExistente = vueloRepository.findById(idVuelo).orElse(null);

            if (vueloExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vuelo no encontrada");
            }

            vueloRepository.delete(vueloExistente);
            return ResponseEntity.status(HttpStatus.OK).body("Vuelo eliminada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerVuelosDisponiblesPorFecha(LocalDate fecha) {
        try {
            List<Vuelo> VuelosDisponibles = vueloRepository.findVuelosDisponiblesPorFecha(fecha);

            if (VuelosDisponibles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Vuelos disponibles para la fecha especificada.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(VuelosDisponibles);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerVuelosDisponiblesPorFechaYTipo(LocalDate fecha, String tipo) {
        try {
            List<Vuelo> VuelosDisponibles = vueloRepository.findVuelosDisponiblesPorFechaYTipo(fecha, tipo);

            if (VuelosDisponibles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Vuelos disponibles para la fecha y tipo especificados.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(VuelosDisponibles);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}



