package com.ProyectoIntegrador.GestionVuelos.service;

import com.ProyectoIntegrador.GestionVuelos.dto.AuthenticationRequest;
import com.ProyectoIntegrador.GestionVuelos.dto.AuthenticationResponse;
import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.repository.ClienteRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    
    private final AuthenticationManager authenticationManager;
    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    
    public AuthenticationService (AuthenticationManager authenticationManager,
                                  ClienteRepository clienteRepository,
                                  JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.clienteRepository = clienteRepository;
        this.jwtService = jwtService;
    }
    
    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );
        
        authenticationManager.authenticate(authToken);

        Cliente cliente = clienteRepository.findByUsername(authRequest.getUsername()).get();
        
        String jwt = jwtService.generateToken(cliente, generateExtraClaims(cliente));
        
        return new AuthenticationResponse(jwt);
        
    }

    private Map<String, Object> generateExtraClaims(Cliente cliente) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",cliente.getNombre());
        extraClaims.put("role", cliente.getRole().name());
        extraClaims.put("permissions", cliente.getAuthorities());

        return extraClaims;
    }

}
