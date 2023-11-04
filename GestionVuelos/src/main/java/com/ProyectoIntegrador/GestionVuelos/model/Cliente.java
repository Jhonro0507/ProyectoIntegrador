package com.ProyectoIntegrador.GestionVuelos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cliente")

public class Cliente {

    private String tipoIdentificacion;
    private String numIdentificacion;
    private String Login;
    private String Pasword;
    private char estado;
    private char permisos;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String correo;

    public List<Cliente> getAllClientes() {
        return null;
    }

    public Cliente getClienteById(Long id) {
        return null;
    }

    public Cliente createCliente(Cliente cliente) {
        return cliente;
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        return cliente;
    }

    public void deleteCliente(Long id) {
    }
}
