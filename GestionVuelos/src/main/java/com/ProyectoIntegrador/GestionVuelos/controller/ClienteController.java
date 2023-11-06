package com.ProyectoIntegrador.GestionVuelos.controller;
import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    
    @Autowired
    public ClienteController (ClienteService clienteService){
        this.clienteService= clienteService;
    }


    @PostMapping
    public ResponseEntity<?> registrarCliente(@RequestBody @Valid Cliente cliente) {
        return clienteService.registrarCliente(cliente);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable UUID idCliente) {
        return clienteService.obtenerClientePorId(idCliente);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        return clienteService.actualizarCliente(id, clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        return clienteService.eliminarCliente(id);
    }

    @GetMapping("/{cedula}/reservas")
    public ResponseEntity<?> obtenerReservasPorIdCliente(@PathVariable UUID idCliente) {
        return clienteService.obtenerReservasPorIdCliente(idCliente);
    }
}


