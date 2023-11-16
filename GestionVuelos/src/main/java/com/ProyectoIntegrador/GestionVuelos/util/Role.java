package com.ProyectoIntegrador.GestionVuelos.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    CUSTOMER(Arrays.asList(Permission.CLIENTE)),
    ADMINISTRATOR(Arrays.asList(Permission.ADMINISTRADOR,Permission.CLIENTE));

    private List<Permission> permissions;

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
