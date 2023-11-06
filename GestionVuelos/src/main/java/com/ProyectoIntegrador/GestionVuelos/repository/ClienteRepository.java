package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findById(UUID idCliente);
}

