package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    @Query("SELECT v " +
            "FROM Vuelo v " +
            "WHERE v NOT IN (SELECT v2 FROM Vuelo v2 JOIN v2.reservas r " +
            "WHERE r.fechaReserva = :fecha) " +
            "AND v.tipo = :tipo")
    List<Vuelo> findVuelosDisponiblesPorFechaYTipo(@Param("fecha") LocalDate fecha, @Param("tipo") String tipo);

    @Query("SELECT v " +
            "FROM Vuelo v " +
            "WHERE v NOT IN (SELECT v2 FROM Vuelo v2 JOIN v2.reservas r " +
            "WHERE r.fechaReserva = :fecha) ")
    List<Vuelo> findVuelosDisponiblesPorFecha(@Param("fecha") LocalDate fecha);
}