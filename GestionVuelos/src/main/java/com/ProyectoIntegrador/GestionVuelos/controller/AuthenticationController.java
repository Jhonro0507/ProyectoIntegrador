package com.ProyectoIntegrador.GestionVuelos.controller;


import com.ProyectoIntegrador.GestionVuelos.dto.AuthenticationRequest;
import com.ProyectoIntegrador.GestionVuelos.dto.AuthenticationResponse;
import com.ProyectoIntegrador.GestionVuelos.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest authRequest){

        AuthenticationResponse jwtDto = authenticationService.login(authRequest);

        return ResponseEntity.ok(jwtDto);
    }

}
