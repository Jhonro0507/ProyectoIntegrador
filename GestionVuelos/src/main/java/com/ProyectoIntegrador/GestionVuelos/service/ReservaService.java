package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.DTO.ReservaDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
            Pasajero pasajero = pasajeroRepository.findById(reserva.getPasajero().getIdPasajero()).orElse(null);
            ResponseEntity<?> validationResult = validarReserva(reserva, pasajero);

            if (validationResult != null) {
                return validationResult;
            }

            // Generar un número de reserva único (GUID)
            UUID numeroReserva = UUID.randomUUID();
            reserva.setIdReserva(numeroReserva);

            // Continuar con el proceso de reserva si la validación es exitosa
            long precioTotal = calcularPrecioTotal(reserva);
            reserva.setTotalPagar(precioTotal);

            Reserva reservaRegistrada = reservaRepository.save(reserva);

            // Mapear la entidad Reserva a ReservaDTO
            ReservaDTO reservaDTO = new ReservaDTO();
            reservaDTO.setIdReserva(reservaRegistrada.getIdReserva());
            reservaDTO.setFechaReserva(reservaRegistrada.getFechaReserva());
            reservaDTO.setTotalPagar(reservaRegistrada.getTotalPagar());

            // Obtener la lista de números de Vuelos
            List<Integer> numerosVuelos = reservaRegistrada.getVuelos()
                    .stream()
                    .map(Vuelo::getNumero)
                    .collect(Collectors.toList());
            reservaDTO.setNumerosVuelos(numerosVuelos);

            return ResponseEntity.status(HttpStatus.CREATED).body(reservaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    private ResponseEntity<?> validarReserva(Reserva reserva, Pasajero pasajero) {
        // Validar la fecha (no nula y posterior a la fecha actual)
        LocalDate fechaActual = LocalDate.now();
        if (reserva.getFechaReserva() == null || reserva.getFechaReserva().isBefore(fechaActual)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha de reserva no es válida.");
        }
        // Validar existencia de pasajero
        if (pasajero == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El pasajero no está registrado.");
        }
        // Validar existencia de Vuelos
        List<Long> VuelosNoEncontradas = new ArrayList<>();

        for (Vuelo vuelo : reserva.getVuelos()) {
            Vuelo vueloExistente = vueloRepository.findById(vuelo.getIdVuelo()).orElse(null);

            if (vueloExistente == null) {
                VuelosNoEncontradas.add(vuelo.getIdVuelo());
            }
        }

        if (!VuelosNoEncontradas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las siguientes Vuelos no existen: " + VuelosNoEncontradas);
        }

        // Verificar disponibilidad de las Vuelos
        List<Vuelo> VuelosDisponibles = vueloRepository.findVuelosDisponiblesPorFecha(reserva.getFechaReserva());
        List<Long> VuelosNoDisponibles = new ArrayList<>();

        for (Vuelo vuelo : reserva.getVuelos()) {
            if (VuelosDisponibles.stream().noneMatch(h -> h.getIdVuelo() == vuelo.getIdVuelo())) {
                VuelosNoDisponibles.add(vuelo.getIdVuelo());
            }
        }

        if (!VuelosNoDisponibles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las siguientes Vuelos no están disponibles en la fecha seleccionada: " + VuelosNoDisponibles);
        }

        return null; // La reserva es válida
    }

    private long calcularPrecioTotal(Reserva reserva) {
        long precioBaseTotal = 0;

        for (Vuelo vuelo : reserva.getVuelos()) {
            long idVuelo = vuelo.getIdVuelo();

            // Buscar el precioBase en la base de datos
            Vuelo vueloConPrecio = vueloRepository.findById(idVuelo).orElse(null);

            if (vueloConPrecio != null) {
                long precioBaseVuelo = vueloConPrecio.getPrecioBase();

                // Aplicar descuento según la fecha de reserva
                LocalDate fechaReserva = reserva.getFechaReserva();
                LocalDate fechaActual = LocalDate.now();
                long diasDeAnticipacion = ChronoUnit.DAYS.between(fechaActual, fechaReserva);

                if (diasDeAnticipacion > 15) {
                    // Aplicar un descuento del 20%
                    precioBaseVuelo -= (long) (precioBaseVuelo * 0.2);

                    // Si la Vuelo es premium, aplicar un descuento adicional del 5%
                    if ("p".equalsIgnoreCase(vueloConPrecio.getTipo())) {
                        precioBaseVuelo -= (long) (precioBaseVuelo * 0.05);
                    }
                }

                precioBaseTotal += precioBaseVuelo;
            }
        }

        return precioBaseTotal;
    }





    public ResponseEntity<?> obtenerReservaPorId(UUID id) {
        try {
            Reserva reserva = reservaRepository.findByIdReserva(id).orElse(null);

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
            Reserva reservaExistente = reservaRepository.findByIdReserva(id).orElse(null);

            if (reservaExistente != null) {
                reservaActualizada.setIdReserva(id);
                Reserva reservaActualizadaResult = reservaRepository.save(reservaActualizada);
                return ResponseEntity.status(HttpStatus.OK).body(reservaActualizadaResult);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada para actualizar.");
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


