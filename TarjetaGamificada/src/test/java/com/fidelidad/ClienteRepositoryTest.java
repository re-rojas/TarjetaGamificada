package com.fidelidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}