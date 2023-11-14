package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.Equipaje;
import com.ProyectoIntegrador.GestionVuelos.model.IdPasajero;
import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipajeRepository extends JpaRepository<Equipaje, Long> {
    Optional<Equipaje> findById(long idEquipaje);

    @Query("SELECT e FROM Equipaje e " +
            "WHERE e.id = :id AND e.pasajero.id = :idPasajero")
    Optional<Equipaje> findByIdYPasajeroId(
            @Param("id") long id,
            @Param("idPasajero") IdPasajero idPasajero
    );


}





