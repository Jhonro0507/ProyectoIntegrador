package com.ProyectoIntegrador.GestionVuelos.controller;
import com.ProyectoIntegrador.GestionVuelos.model.Asiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asientos")
public class AsientoController {

    private final Asiento asientoService;

    @Autowired
    public AsientoController(Asiento asientoService) {
        this.asientoService = asientoService;
    }

    @GetMapping("/")
    public List<Asiento> getAllAsientos() {
        return asientoService.getAllAsientos();
    }

    @GetMapping("/{id}")
    public Asiento getAsientoById(@PathVariable Long id) {
        return asientoService.getAsientoById(id);
    }

    @PostMapping("/")
    public Asiento createAsiento(@RequestBody Asiento asiento) {
        return asientoService.createAsiento(asiento);
    }

    @PutMapping("/{id}")
    public Asiento updateAsiento(@PathVariable Long id, @RequestBody Asiento asiento) {
        return asientoService.updateAsiento(id, asiento);
    }

    @DeleteMapping("/{id}")
    public void deleteAsiento(@PathVariable Long id) {
        asientoService.deleteAsiento(id);
    }
}

