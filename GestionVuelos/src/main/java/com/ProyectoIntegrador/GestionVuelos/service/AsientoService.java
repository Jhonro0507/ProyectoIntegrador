package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.model.Asiento;
import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import com.ProyectoIntegrador.GestionVuelos.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoService {

    private final AsientoRepository asientoRepository;

    @Autowired
    public AsientoService (AsientoRepository asientoRepository){
        this.asientoRepository=asientoRepository;
    }

    public ResponseEntity<?> asientosDisponiblesEnVuelo(long vueloId) {
        try {
            List<Asiento> asientosDisponibles = asientoRepository.asientosDisponiblesEnVuelo(vueloId);
            if (asientosDisponibles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay asientos disponibles en el vuelo especificado.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(asientosDisponibles);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
