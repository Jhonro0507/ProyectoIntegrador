package com.ProyectoIntegrador.Pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private UUID reservaId;
    @Column(nullable = false)
    private Double totalPagar;
    @Getter
    @Column(nullable = false)
    private boolean pagada;
    @Column
    private String metodoPago;
    @Column
    private LocalDate fechaPago;
    @Column
    private LocalTime horaPago;

    public boolean getPagada (){
        return pagada;
    };
}
