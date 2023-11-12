package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.repository.ClienteRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;
    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ReservaRepository reservaRepository) {
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
    }

    public ResponseEntity<?> registrarCliente(Cliente cliente) {
        try {
            Cliente clienteRegistrado = clienteRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerClientePorId(UUID idCliente) {
        try {
            Cliente cliente = clienteRepository.findById(idCliente).orElse(null);

            if (cliente != null) {
                return ResponseEntity.status(HttpStatus.OK).body(cliente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente con id " + idCliente + " no se encontró");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerTodosLosClientes() {
        try {
            List<Cliente> clientes = clienteRepository.findAll();

            if (!clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(clientes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron clientes");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> actualizarCliente(Long id, Cliente clienteActualizado) {
        try {
            Cliente clienteExistente = clienteRepository.findById(id).orElse(null);

            if (clienteExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Cliente con id " + id + " no se encontró");
            }

            if (clienteActualizado.getUsuario() != null) {
                clienteExistente.setUsuario(clienteActualizado.getUsuario());
            }
            if (clienteActualizado.getPassword() != null) {
                clienteExistente.setPassword(clienteActualizado.getPassword());
            }
            if (clienteActualizado.getEmail() != null) {
                clienteExistente.setEmail(clienteActualizado.getEmail());
            }
            if (clienteActualizado.getNombre() != null) {
                clienteExistente.setNombre(clienteActualizado.getNombre());
            }
            if (clienteActualizado.getDireccion() != null) {
                clienteExistente.setDireccion(clienteActualizado.getDireccion());
            }
            if (clienteActualizado.getTelefono() != null) {
                clienteExistente.setTelefono(clienteActualizado.getTelefono());
            }
            if (clienteActualizado.getImagenPerfil() != null) {
                clienteExistente.setImagenPerfil(clienteActualizado.getImagenPerfil());
            }
            if (clienteActualizado.getAdministrador() != null) {
                clienteExistente.setAdministrador(clienteActualizado.getAdministrador());
            }
            clienteRepository.save(clienteExistente);

            return ResponseEntity.status(HttpStatus.OK).body(clienteExistente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> eliminarCliente(Long id) {
        try {
            Cliente clienteExistente = clienteRepository.findById(id).orElse(null);

            if (clienteExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Cliente con id " + id + " no se encontró");
            }

            clienteRepository.delete(clienteExistente);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerReservasPorIdCliente(UUID idCliente) {
        try {
            List<Reserva> reservas = reservaRepository.obtenerReservasPorIdCliente(idCliente);
            return ResponseEntity.status(HttpStatus.OK).body(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
