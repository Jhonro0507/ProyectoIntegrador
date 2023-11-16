package com.ProyectoIntegrador.GestionVuelos.model;

import com.ProyectoIntegrador.GestionVuelos.util.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente implements UserDetails {

    @Id
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column
    private String direccion;
    @Column(nullable = false)
    private String telefono;
    @Column
    private String imagenPerfil;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;





    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Reserva> reservas = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = role.getPermissions().stream()
                .map(permissionEmun -> new SimpleGrantedAuthority(permissionEmun.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("Role_" + role.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
