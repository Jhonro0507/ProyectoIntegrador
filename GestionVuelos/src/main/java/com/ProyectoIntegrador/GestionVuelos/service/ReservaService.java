package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.DTO.PagoDTO;
import com.ProyectoIntegrador.GestionVuelos.DTO.ReservaDTO;
import com.ProyectoIntegrador.GestionVuelos.DTO.RespuestaCreacionPagoDTO;
import com.ProyectoIntegrador.GestionVuelos.client.PagoClient;
import com.ProyectoIntegrador.GestionVuelos.model.*;
import com.ProyectoIntegrador.GestionVuelos.repository.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final PasajeroRepository pasajeroRepository;
    private final VueloRepository vueloRepository;
    private final EquipajeRepository equipajeRepository;
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;
    private final AsientoRepository asientoRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, PasajeroRepository pasajeroRepository, VueloRepository vueloRepository,
                          EquipajeRepository equipajeRepository, MascotaRepository mascotaRepository, ClienteRepository clienteRepository,
                          AsientoRepository asientoRepository) {
        this.reservaRepository = reservaRepository;
        this.pasajeroRepository = pasajeroRepository;
        this.vueloRepository = vueloRepository;
        this.equipajeRepository = equipajeRepository;
        this.mascotaRepository = mascotaRepository;
        this.clienteRepository = clienteRepository;
        this.asientoRepository = asientoRepository;
    }

    public ResponseEntity<?> registrarReserva(Reserva reserva) {
        try {
            ResponseEntity<?> validationResult = validarReserva(reserva);

            if (!(validationResult.getBody() instanceof Reserva)) {
                return validationResult;
            }
            Reserva reservaValidada = (Reserva) validationResult.getBody();

            reservaValidada.setFecha(LocalDate.now());
            reservaValidada.setHora(LocalTime.now());
            reservaValidada.setTotalPagar(calcularPrecioTotal(reservaValidada));
            reservaValidada.setEstado("pendiente");
            reservaValidada.setPagada(false);


            Reserva reservaRegistrada = reservaRepository.save(reservaValidada);


            return ResponseEntity.status(HttpStatus.CREATED).body(reservaRegistrada);
        } catch (Exception e) {
            // Manejar la excepción aquí (puede imprimir el stack trace o realizar otras acciones)
            e.printStackTrace(); // O manejar de otra manera según tus necesidades
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al registrar la reserva");
        }
    }



    private ResponseEntity<?> validarReserva(Reserva reserva) {
        try {
            Reserva reservaValidada = new Reserva();
            Cliente cliente = reserva.getCliente();
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La reserva no tiene un cliente");
            }
            Cliente clienteExistente = clienteRepository.findById(cliente.getId()).orElse(null);
            if (clienteExistente == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente con id " + cliente.getId() + " no existe");
            }
            reservaValidada.setCliente(clienteExistente);

            List<IdPasajero> pasajerosNoEncontrados = new ArrayList<>();
            List<Pasajero> pasajerosValidados = new ArrayList<>();
            int totalAsientos = 0;
            if (reserva.getPasajeros() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La reserva no contiene pasajeros");
            }
            for (Pasajero pasajero : reserva.getPasajeros()) {
                IdPasajero idPasajero = pasajero.getId();
                Pasajero pasajeroExistente = pasajeroRepository.findById(idPasajero).orElse(null);
                if (pasajeroExistente == null) {
                    pasajerosNoEncontrados.add(idPasajero);
                } else {
                    totalAsientos += 1;

                    List<Long> equipajesNoEncontrados = new ArrayList<>();
                    List<Equipaje> equipajesValidados = new ArrayList<>();
                    for (Equipaje equipaje : pasajero.getEquipajes()) {
                        Equipaje equipajeExistente = equipajeRepository.findByIdYPasajeroId(equipaje.getId(), idPasajero).orElse(null);
                        if (equipajeExistente == null) {
                            equipajesNoEncontrados.add(equipaje.getId());
                        } else {
                            equipajesValidados.add(equipajeExistente);
                        }
                    }

                    List<Long> mascotasNoEncontradas = new ArrayList<>();
                    List<Mascota> mascotasValidadas = new ArrayList<>();
                    for (Mascota mascota : pasajero.getMascotas()) {
                        Mascota mascotaExistente = mascotaRepository.findByIdYPasajeroId(mascota.getId(), idPasajero).orElse(null);
                        if (mascotaExistente == null) {
                            mascotasNoEncontradas.add(mascota.getId());
                        } else {
                            totalAsientos += 1;
                            mascotasValidadas.add(mascotaExistente);
                        }
                    }

                    if (!equipajesNoEncontrados.isEmpty() || !mascotasNoEncontradas.isEmpty()) {
                        // Crear un objeto JSON para la respuesta
                        Map<String, Object> jsonResponse = new HashMap<>();
                        jsonResponse.put("error", "Estos equipajes y/o mascotas del pasajero no existen");
                        jsonResponse.put("pasajeroId", pasajero.getId());
                        jsonResponse.put("ID equipajes no encontrados", equipajesNoEncontrados);
                        jsonResponse.put("ID mascotas no encontradas", mascotasNoEncontradas);
                        // Devolver una respuesta JSON con un estado HTTP de BadRequest (400)
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
                    }

                    pasajeroExistente.setEquipajes(equipajesValidados);
                    pasajeroExistente.setMascotas(mascotasValidadas);
                    pasajerosValidados.add(pasajeroExistente);
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
            reservaValidada.setPasajeros(pasajerosValidados);

            // Validar existencia de Vuelos
            if (reserva.getVuelos() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La reserva no tiene vuelo");
            }
            List<Long> vuelosNoEncontrados = new ArrayList<>();
            List<Vuelo> vuelosValidados = new ArrayList<>();
            for (Vuelo vuelo : reserva.getVuelos()) {
                Vuelo vueloExistente = vueloRepository.findById(vuelo.getId()).orElse(null);
                if (vueloExistente == null) {
                    vuelosNoEncontrados.add(vuelo.getId());
                } else {
                    if (totalAsientos != vuelo.getAsientos().size()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad de asientos (" + vuelo.getAsientos().size() +
                                ") y la cantidad de pasajeros y mascotas (" + totalAsientos + ") debe ser la misma en el vuelo con ID: " + vuelo.getId());
                    }
                    List<Asiento> asientosValidados = new ArrayList<>();
                    // Validar disponibilidad de los asientos
                    List<IdAsiento> asientosNoEncontrados = new ArrayList<>();
                    for (Asiento asiento : vuelo.getAsientos()) {
                        Asiento asientoExistente = asientoRepository.disponibilidadAsientoEnVuelo(vuelo.getId(), asiento.getId()).orElse(null);
                        if (asientoExistente == null) {
                            asientosNoEncontrados.add(asiento.getId());
                        } else {
                            asientoExistente.setEstado("reservado");
                            asientosValidados.add(asientoExistente);
                            asientoRepository.save(asientoExistente);
                        }
                    }
                    if (!asientosNoEncontrados.isEmpty()) {
                        Map<String, Object> jsonResponse = new HashMap<>();
                        jsonResponse.put("error", "Estos asientos no existen o no están disponibles");
                        jsonResponse.put("vueloID", vuelo.getId());
                        jsonResponse.put("ID asientos", asientosNoEncontrados);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
                    }
                    vueloExistente.setAsientos(asientosValidados);
                    vuelosValidados.add(vueloExistente);
                }
            }
            if (!vuelosNoEncontrados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las siguientes Vuelos no existen: " + vuelosNoEncontrados);
            }
            reservaValidada.setVuelos(vuelosValidados);

            return ResponseEntity.status(HttpStatus.OK).body(reservaValidada);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al validar la reserva");
        }
    }




    private double calcularPrecioTotal(Reserva reserva) {
        double precioAsientoBase = 100000;
        double precioBaseTotal = 50000;
        for (Vuelo vuelo : reserva.getVuelos()){
            for (Asiento asiento : vuelo.getAsientos()){
                if (Objects.equals(asiento.getClase(), "E")){
                    precioBaseTotal += precioAsientoBase;
                } else if (Objects.equals(asiento.getClase(), "EP")) {
                    precioBaseTotal += precioAsientoBase*1.3;
                } else if (Objects.equals(asiento.getClase(), "B")) {
                    precioBaseTotal += precioAsientoBase*1.6;
                } else if (Objects.equals(asiento.getClase(), "P")) {
                    precioBaseTotal += precioAsientoBase*2;
                }
            }
        }
        double precioEquipajeBase = precioAsientoBase*0.5;
        double precioEquipajeBaseTotal = precioAsientoBase*0.25;
        for (Pasajero pasajero : reserva.getPasajeros()){
            for (Equipaje equipaje : pasajero.getEquipajes()){
                if (equipaje.isBodega()){
                    precioEquipajeBaseTotal += precioEquipajeBase*2*equipaje.getPeso()/10;
                }
                precioEquipajeBaseTotal += precioEquipajeBase*equipaje.getPeso()/10;
            }
        }
        return precioBaseTotal+precioEquipajeBaseTotal;
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

    public ResponseEntity<?> pagarReserva(PagoDTO pagoDTO) {
        try {
            Reserva reservaExistente = reservaRepository.findById(pagoDTO.getReservaId()).orElse(null);

            if (reservaExistente == null) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva " + pagoDTO.getReservaId() + " no encontrada para realizar pago.");
            }

            reservaExistente.setEstado("confirmada");
            reservaExistente.setPagada(true);

            reservaExistente.getVuelos().stream()
                    .flatMap(vuelo -> vuelo.getAsientos().stream())
                    .forEach(asiento -> asiento.setEstado("ocupado"));

            Reserva reservaActualizadaResult = reservaRepository.save(reservaExistente);


            return ResponseEntity.status(HttpStatus.OK).body(pagoDTO);

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


