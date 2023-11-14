package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.Asiento;
import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.model.IdAsiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {

    Optional<Asiento> findById(IdAsiento idAsiento);

    @Query("SELECT COUNT(a) FROM Asiento a WHERE a.estado = 'disponible' AND a.vuelo.id = :vueloId")
    long contarAsientosDisponiblesEnVuelo(@Param("vueloId") long vueloId);

    @Query("SELECT a FROM Asiento a WHERE a.estado = 'disponible' AND a.vuelo.id = :vueloId")
    List<Asiento> asientosDisponiblesEnVuelo(@Param("vueloId") long vueloId);

    @Query("SELECT a FROM Asiento a WHERE a.estado = 'disponible' AND a.id = :idAsiento AND a.vuelo.id = :vueloId")
    Optional<Asiento> disponibilidadAsientoEnVuelo(@Param("vueloId") long vueloId,
                                             @Param("idAsiento") IdAsiento idAsiento);
}

