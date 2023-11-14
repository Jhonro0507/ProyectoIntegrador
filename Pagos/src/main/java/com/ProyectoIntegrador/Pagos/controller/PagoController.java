package com.ProyectoIntegrador.Pagos.controller;

import com.ProyectoIntegrador.Pagos.model.Pago;
import com.ProyectoIntegrador.Pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pagos")
public class PagoController {
    private final PagoService pagoService;
    @Autowired
    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrarPago(@RequestBody Pago pago) {
        return pagoService.registrarPago(pago);
    }

    @GetMapping("/{idPago}")
    public ResponseEntity<?> obtenerPagoPorId(@PathVariable long idPago) {
        return pagoService.obtenerPagoPorId(idPago);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosPagos() {
        return pagoService.obtenerTodosLosPagos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long id, @RequestBody Pago pagoActualizado) {
        return pagoService.actualizarPago(id, pagoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long id) {
        return pagoService.eliminarPago(id);
    }


}
