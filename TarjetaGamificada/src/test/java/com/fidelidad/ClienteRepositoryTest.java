package com.fidelidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ClienteRepositoryTest {

    @Test
    void alAgregarUnCliente_deberiaPoderEncontrarloPorId() {
        // Arrange 
        ClienteRepository repo = new ClienteRepository();
        Cliente nuevoCliente = new Cliente("id-1", "Juan Perez", "juan@test.com");

        // Act
        repo.guardar(nuevoCliente);
        Cliente clienteEncontrado = repo.buscarPorId("id-1");

        // Assert
        assertNotNull(clienteEncontrado);
        assertEquals("Juan Perez", clienteEncontrado.getNombre());
    }

    @Test
    void alGuardarClienteConCorreoInvalido_deberiaLanzarExcepcion() {
        // Arrange
        ClienteRepository repo = new ClienteRepository();
        Cliente clienteInvalido = new Cliente("id-2", "Luis", "luis.correo.com"); // Sin @

        // Act & Assert
        // la prueba espera que se lance una excepción cuando intentemos guardar el cliente invalido
        assertThrows(IllegalArgumentException.class, () -> {
            repo.guardar(clienteInvalido);
        });
    }

    @Test
    void alGuardarVariosClientes_listarTodosDebeDevolverlos() {
        // Arrange
        ClienteRepository repo = new ClienteRepository();
        Cliente cliente1 = new Cliente("id-1", "Ana", "ana@test.com");
        Cliente cliente2 = new Cliente("id-2", "Luis", "luis@test.com");
        repo.guardar(cliente1);
        repo.guardar(cliente2);

        // Act
        List<Cliente> clientes = repo.listarTodos();

        // Assert
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
    }

    @Test
    void alActualizarUnCliente_susDatosDebenCambiar() {
        // Arrange
        ClienteRepository repo = new ClienteRepository();
        Cliente clienteOriginal = new Cliente("id-1", "Ana", "ana@test.com");
        repo.guardar(clienteOriginal);

        // Act
        Cliente clienteActualizado = new Cliente("id-1", "Ana Garcia", "ana.garcia@test.com");
        repo.actualizar(clienteActualizado);

        // Assert
        Cliente clienteEncontrado = repo.buscarPorId("id-1");
        assertEquals("Ana Garcia", clienteEncontrado.getNombre());
        assertEquals("ana.garcia@test.com", clienteEncontrado.getCorreo());
    }

    @Test
    void alEliminarUnCliente_noDeberiaSerEncontrado() {
        // Arrange
        ClienteRepository repo = new ClienteRepository();
        Cliente cliente = new Cliente("id-1", "Ana", "ana@test.com");
        repo.guardar(cliente);

        // Act
        repo.eliminar("id-1");

        // Assert
        Cliente clienteEncontrado = repo.buscarPorId("id-1");
        assertNull(clienteEncontrado);
    }

}