package com.ProyectoIntegrador.GestionVuelos.repository;

import com.ProyectoIntegrador.GestionVuelos.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    @Query("SELECT v " +
            "FROM Vuelo v " +
            "WHERE v NOT IN (SELECT v2 FROM Vuelo v2 JOIN v2.reservas r " +
            "WHERE r.fecha = :fecha) " +
            "AND v.precio = :precio")
    List<Vuelo> findVuelosDisponiblesPorFechaYTipo(@Param("fecha") LocalDate fecha, @Param("precio") String tipo);

    @Query("SELECT v " +
            "FROM Vuelo v " +
            "WHERE v NOT IN (SELECT v2 FROM Vuelo v2 JOIN v2.reservas r " +
            "WHERE r.fecha = :fecha) ")
    List<Vuelo> findVuelosDisponiblesPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT v FROM Vuelo v " +
            "WHERE (v.ciudadOrigen = :origen OR v.ciudadDestino = :destino) " +
            "AND v.asientosDisponibles != 0 " +
            "AND (v.fechaSalida > :fechaMinima OR (v.fechaSalida = :fechaMinima AND v.horaSalida > :horaMinima))")
    List<Vuelo> findVuelosDisponiblesPorOrigenYDestino(
            @Param("origen") String origen,
            @Param("destino") String destino,
            @Param("fechaMinima") LocalDate fechaMinima,
            @Param("horaMinima") LocalTime horaMinima
    );





}