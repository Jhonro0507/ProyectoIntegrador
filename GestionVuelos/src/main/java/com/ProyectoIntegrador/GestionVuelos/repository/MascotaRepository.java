package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.Equipaje;
import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    Optional<Mascota> findById(long idMascota);

    @Query("SELECT m FROM Mascota m " +
            "WHERE m.id = :id AND m.pasajero.id = :idPasajero")
    Optional<Mascota> findByIdYPasajeroId(
            @Param("id") long id,
            @Param("idPasajero") IdPasajero idPasajero
    );
}
