package com.ProyectoIntegrador.GestionVuelos.controller;
import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{cedula}/reservas")
    public ResponseEntity<?> obtenerReservasPorIdCliente(@PathVariable UUID idCliente) {
        return clienteService.obtenerReservasPorIdCliente(idCliente);
    }
}


