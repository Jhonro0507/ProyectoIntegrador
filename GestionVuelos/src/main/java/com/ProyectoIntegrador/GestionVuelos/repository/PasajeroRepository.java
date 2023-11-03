package com.ProyectoIntegrador.GestionVuelos.repository;


import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
}