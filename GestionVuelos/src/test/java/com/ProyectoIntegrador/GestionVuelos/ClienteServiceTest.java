package com.ProyectoIntegrador.GestionVuelos;

import com.ProyectoIntegrador.GestionVuelos.model.Cliente;
import com.ProyectoIntegrador.GestionVuelos.model.Reserva;
import com.ProyectoIntegrador.GestionVuelos.repository.ClienteRepository;
import com.ProyectoIntegrador.GestionVuelos.repository.ReservaRepository;
import com.ProyectoIntegrador.GestionVuelos.service.ClienteService;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;
    private ClienteService clienteService;

    //3 Create the instances to be tested

    @BeforeEach
    public void setUp() {
        // mocks
        this.clienteRepository = mock(ClienteRepository.class);
        this.reservaRepository = mock(ReservaRepository.class);
        this.clienteService = new ClienteService(this.clienteRepository, this.reservaRepository);
    }

    @Test
    public void registrarClienteExitoso() {
        // Arrange
        Cliente cliente = new Cliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        ResponseEntity<?> responseEntity = this.clienteService.registrarCliente(cliente);

        // Assert
        verify(clienteRepository, times(1)).save(cliente);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(cliente, responseEntity.getBody());
    }

    @Test
    public void testRegistrarClienteNoExistoso() {
        // Arrange
        Cliente cliente = new Cliente();
        when(clienteRepository.save(cliente)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = this.clienteService.registrarCliente(cliente);

        // Assert
        verify(clienteRepository, times(1)).save(cliente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerClientePorIdExitoso() {
        // Arrange
        UUID idCliente = UUID.randomUUID();
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerClientePorId(idCliente);

        // Assert
        verify(clienteRepository, times(1)).findById(idCliente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(cliente, responseEntity.getBody());
    }

    @Test
    public void testObtenerClientePorIdNoEncontrado() {
        // Arrange
        UUID idCliente = UUID.randomUUID();
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerClientePorId(idCliente);

        // Assert
        verify(clienteRepository, times(1)).findById(idCliente);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("El cliente con id " + idCliente + " no se encontró", responseEntity.getBody());
    }

    @Test
    public void testObtenerClientePorIdFallo() {
        // Arrange
        UUID idCliente = UUID.randomUUID();
        when(clienteRepository.findById(idCliente)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerClientePorId(idCliente);

        // Assert
        verify(clienteRepository, times(1)).findById(idCliente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosClientesExito() {
        // Arrange
        List<Cliente> clientes = Arrays.asList(new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerTodosLosClientes();

        // Assert
        verify(clienteRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(clientes, responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosClientesNoEncontrado() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerTodosLosClientes();

        // Assert
        verify(clienteRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("No se encontraron clientes", responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosClientesFallo() {
        // Arrange
        when(clienteRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerTodosLosClientes();

        // Assert
        verify(clienteRepository, times(1)).findAll();
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testActualizarClienteExito() {
        // Arrange
        Long id = 1L;
        Cliente clienteExistente = new Cliente();
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setUsuario("Pepito perez");
        clienteActualizado.setPassword("password");
        clienteActualizado.setEmail("lucho@gmail.com");
        clienteActualizado.setNombre("Pepito");
        clienteActualizado.setDireccion("Calle falsa 123");
        clienteActualizado.setTelefono("1234567");
        clienteActualizado.setImagenPerfil("tttpa");
        clienteActualizado.setAdministrador(true);


        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

        // Act
        ResponseEntity<?> responseEntity = clienteService.actualizarCliente(id, clienteActualizado);

        // Assert
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(clienteExistente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(clienteExistente, responseEntity.getBody());
    }

    @Test
    public void testActualizarClienteNoEncontrado() {
        // Arrange
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = clienteService.actualizarCliente(id, new Cliente());

        // Assert
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).save(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("El Cliente con id " + id + " no se encontró", responseEntity.getBody());
    }

    @Test
    public void testActualizarClienteFallo() {
        // Arrange
        Long id = 1L;
        Cliente clienteExistente = new Cliente(/* provide necessary client data */);
        Cliente clienteActualizado = new Cliente(/* provide updated client data */);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(clienteExistente)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = clienteService.actualizarCliente(id, clienteActualizado);

        // Assert
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(clienteExistente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testEliminarClienteSuccess() {
        // Arrange
        Long id = 1L;
        Cliente clienteExistente = new Cliente(/* provide necessary client data */);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

        // Act
        ResponseEntity<?> responseEntity = clienteService.eliminarCliente(id);

        // Assert
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).delete(clienteExistente);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assertions.assertEquals("Cliente eliminado con éxito", responseEntity.getBody());
    }

    @Test
    public void testEliminarClienteNotFound() {
        // Arrange
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = clienteService.eliminarCliente(id);

        // Assert
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).delete(any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("El Cliente con id " + id + " no se encontró", responseEntity.getBody());
    }

    @Test
    public void testEliminarClienteFailure() {
        // Arrange
        Long id = 1L;
        Cliente clienteExistente = new Cliente(/* provide necessary client data */);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));
        doThrow(new RuntimeException("Test Exception")).when(clienteRepository).delete(clienteExistente);

        // Act
        ResponseEntity<?> responseEntity = clienteService.eliminarCliente(id);

        // Assert
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).delete(clienteExistente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }

    @Test
    public void testObtenerReservasPorIdClienteSuccess() {
        // Arrange
        UUID idCliente = UUID.randomUUID();
        List<Reserva> reservas = Arrays.asList(new Reserva(/* provide necessary reservation data */));
        when(reservaRepository.obtenerReservasPorIdCliente(idCliente)).thenReturn(reservas);

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerReservasPorIdCliente(idCliente);

        // Assert
        verify(reservaRepository, times(1)).obtenerReservasPorIdCliente(idCliente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(reservas, responseEntity.getBody());
    }

    @Test
    public void testObtenerReservasPorIdClienteFailure() {
        // Arrange
        UUID idCliente = UUID.randomUUID();
        when(reservaRepository.obtenerReservasPorIdCliente(idCliente)).thenThrow(new RuntimeException("Test Exception"));

        // Act
        ResponseEntity<?> responseEntity = clienteService.obtenerReservasPorIdCliente(idCliente);

        // Assert
        verify(reservaRepository, times(1)).obtenerReservasPorIdCliente(idCliente);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Test Exception", responseEntity.getBody());
    }


    public static class VueloServiceTest {

    }
}
