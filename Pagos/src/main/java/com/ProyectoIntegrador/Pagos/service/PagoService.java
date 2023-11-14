package com.ProyectoIntegrador.Pagos.service;

import com.ProyectoIntegrador.Pagos.model.Pago;
import com.ProyectoIntegrador.Pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    @Autowired
    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }
    public ResponseEntity<?> registrarPago(Pago pago) {
        try {
            Pago pagoRegistrado = pagoRepository.save(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerPagoPorId(long idPago) {
        try {
            Pago pago = pagoRepository.findById(idPago).orElse(null);

            if (pago != null) {
                return ResponseEntity.status(HttpStatus.OK).body(pago);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El pago con id " + idPago + " no se encontró");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> obtenerTodosLosPagos() {
        try {
            List<Pago> pagos = pagoRepository.findAll();

            if (!pagos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(pagos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron pagos");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> actualizarPago(Long id, Pago pagoActualizado) {
        try {
            Pago pagoExistente = pagoRepository.findById(id).orElse(null);

            if (pagoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Pago con id " + id + " no se encontró");
            }
            if (pagoActualizado.getReservaId() != null) {
                pagoExistente.setReservaId(pagoActualizado.getReservaId());
            }
            if (pagoActualizado.getTotalPagar() != null) {
                pagoExistente.setTotalPagar(pagoActualizado.getTotalPagar());
            }
            if (pagoActualizado.getMetodoPago() != null){
                pagoExistente.setMetodoPago(pagoActualizado.getMetodoPago());
            }
            pagoExistente.setPagada(pagoActualizado.getPagada());

            pagoRepository.save(pagoExistente);

            return ResponseEntity.status(HttpStatus.OK).body(pagoExistente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> eliminarPago(Long id) {
        try {
            Pago pagoExistente = pagoRepository.findById(id).orElse(null);

            if (pagoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Pago con id " + id + " no se encontró");
            }

            pagoRepository.delete(pagoExistente);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pago eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
