package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.DTO.ReservaDTO;
import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.repository.PasajeroRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.VueloRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final PasajeroRepository pasajeroRepository;
    private final VueloRepository vueloRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, PasajeroRepository pasajeroRepository, VueloRepository vueloRepository) {
        this.reservaRepository = reservaRepository;
        this.pasajeroRepository = pasajeroRepository;
        this.vueloRepository = vueloRepository;
    }

    public ResponseEntity<?> registrarReserva(Reserva reserva) {
        try {

            List<Pasajero> pasajeros = reserva.getPasajeros();
            List<IdPasajero> pasajerosNoEncontrados = new ArrayList<>();

            for (Pasajero pasajero : reserva.getPasajeros()) {
                Pasajero pasajeroExistente = pasajeroRepository.findById(pasajero.getId()).orElse(null);
                if (pasajeroExistente == null) {
                    pasajerosNoEncontrados.add(pasajero.getId());
                }
            }

            if (!pasajerosNoEncontrados.isEmpty()) {
                // Crear un objeto JSON para la respuesta
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("error", "Estos pasajeros no existen");
                jsonResponse.put("pasajerosNoEncontrados", pasajerosNoEncontrados);

                // Devolver una respuesta JSON con un estado HTTP de BadRequest (400)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
            }


            ResponseEntity<?> validationResult = validarReserva(reserva);


            if (validationResult != null) {
                return validationResult;
            }

            // Generar un número de reserva único (GUID)
            UUID numeroReserva = UUID.randomUUID();
            reserva.setId(numeroReserva);

            // Continuar con el proceso de reserva si la validación es exitosa
            double precioTotal = calcularPrecioTotal(reserva);
            reserva.setTotalPagar(precioTotal);

            Reserva reservaRegistrada = reservaRepository.save(reserva);

            // Mapear la entidad Reserva a ReservaDTO
            ReservaDTO reservaDTO = new ReservaDTO();
            reservaDTO.setId(reservaRegistrada.getId());
            reservaDTO.setFecha(reservaRegistrada.getFecha());
            reservaDTO.setTotalPagar(reservaRegistrada.getTotalPagar());

            // Obtener la lista de números de Vuelos
            List<String> numerosVuelos = reservaRegistrada.getVuelos()
                    .stream()
                    .map(Vuelo::getNumeroVuelo)
                    .collect(Collectors.toList());
            reservaDTO.setNumerosVuelos(numerosVuelos);

            return ResponseEntity.status(HttpStatus.CREATED).body(reservaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    private ResponseEntity<?> validarReserva(Reserva reserva) {
        // Validar la fecha (no nula y posterior a la fecha actual)
        LocalDate fechaActual = LocalDate.now();
        if (reserva.getFecha() == null || reserva.getFecha().isBefore(fechaActual)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha de reserva no es válida.");
        }

        // Validar existencia de Vuelos
        List<Long> vuelosNoEncontrados = new ArrayList<>();

        for (Vuelo vuelo : reserva.getVuelos()) {
            Vuelo vueloExistente = vueloRepository.findById(vuelo.getId()).orElse(null);

            if (vueloExistente == null) {
                vuelosNoEncontrados.add(vuelo.getId());
            }
        }

        if (!vuelosNoEncontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las siguientes Vuelos no existen: " + vuelosNoEncontrados);
        }

        // Verificar disponibilidad de los Vuelos
        List<Vuelo> vuelosDisponibles = vueloRepository.findVuelosDisponiblesPorFecha(reserva.getFecha());
        List<Long> vuelosNoDisponibles = new ArrayList<>();

        for (Vuelo vuelo : reserva.getVuelos()) {
            if (vuelosDisponibles.stream().noneMatch(h -> h.getId() == vuelo.getId())) {
                vuelosNoDisponibles.add(vuelo.getId());
            }
        }

        if (!vuelosNoDisponibles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las siguientes Vuelos no están disponibles en la fecha seleccionada: " + vuelosNoDisponibles);
        }

        return null; // La reserva es válida
    }


    private double calcularPrecioTotal(Reserva reserva) {
        double precioBaseTotal = 0;

        for (Vuelo vuelo : reserva.getVuelos()) {
            long idVuelo = vuelo.getId();

            // Buscar el precioBase en la base de datos
            Vuelo vueloConPrecio = vueloRepository.findById(idVuelo).orElse(null);

            if (vueloConPrecio != null) {
                double precioBaseVuelo = vueloConPrecio.getPrecio();

                // Aplicar descuento según la fecha de reserva
                LocalDate fechaReserva = reserva.getFecha();
                LocalDate fechaActual = LocalDate.now();
                long diasDeAnticipacion = ChronoUnit.DAYS.between(fechaActual, fechaReserva);

                if (diasDeAnticipacion > 15) {
                    // Aplicar un descuento del 20%
                    precioBaseVuelo -= (precioBaseVuelo * 0.2);

                    // Si la Vuelo es premium, aplicar un descuento adicional del 5%
                    if ("p".equalsIgnoreCase(vueloConPrecio.getCiudadDestino())) {
                        precioBaseVuelo -= (precioBaseVuelo * 0.05);
                    }
                }

                precioBaseTotal += precioBaseVuelo;
            }
        }

        return precioBaseTotal;
    }





    public ResponseEntity<?> obtenerReservaPorId(UUID id) {
        try {
            Reserva reserva = reservaRepository.findById(id).orElse(null);

            if (reserva != null) {
                return ResponseEntity.status(HttpStatus.OK).body(reserva);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerTodasLasReservas() {
        try {
            List<Reserva> reservas = reservaRepository.findAll();

            if (!reservas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(reservas);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay reservas registradas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> actualizarReserva(UUID id, Reserva reservaActualizada) {
        try {
            Reserva reservaExistente = reservaRepository.findById(id).orElse(null);

            if (reservaExistente != null) {
                reservaActualizada.setId(id);
                Reserva reservaActualizadaResult = reservaRepository.save(reservaActualizada);
                return ResponseEntity.status(HttpStatus.OK).body(reservaActualizadaResult);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada para actualizar.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> pagarReserva(UUID id, ResponseEntity<Boolean> pagoRealizado) {
        try {
            if (pagoRealizado.getBody()==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cuerpo del pago es "+null);
            }

            if (!pagoRealizado.getBody()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pagoRealizado.getBody());
            }

            Reserva reservaExistente = reservaRepository.findById(id).orElse(null);

            if (reservaExistente != null) {
                reservaExistente.setPagada(pagoRealizado.getBody());
                reservaRepository.save(reservaExistente);
                return ResponseEntity.status(HttpStatus.OK).body(reservaExistente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada para realizar pago.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> eliminarReserva(Long id) {
        try {
            Reserva reservaExistente = reservaRepository.findById(id).orElse(null);

            if (reservaExistente != null) {
                reservaRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada para eliminar.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}


