package com.ProyectoIntegrador.Pagos.repository;

import com.ProyectoIntegrador.Pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findById(long id);
}
