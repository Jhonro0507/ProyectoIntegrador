package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findById(UUID id);
    @Query("SELECT r FROM Reserva r JOIN r.pasajeros p WHERE p.id = :idPasajero")
    List<Reserva> obtenerReservasPorIdPasajero(@Param("idPasajero") IdPasajero idPasajero);
}
