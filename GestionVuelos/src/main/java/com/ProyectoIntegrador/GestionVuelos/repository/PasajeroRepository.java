package com.ProyectoIntegrador.GestionVuelos.repository;


import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    Optional<Pasajero> findById(IdPasajero idPasajero);
}