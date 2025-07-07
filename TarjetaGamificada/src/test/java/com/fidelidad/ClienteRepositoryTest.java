package com.fidelidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {

    @Test
    void alAgregarUnCliente_deberiaPoderEncontrarloPorId() {
        // Arrange (Preparar)
        ClienteRepository repo = new ClienteRepository();
        Cliente nuevoCliente = new Cliente("id-1", "Juan Perez", "juan@test.com");

        // Act (Actuar)
        repo.guardar(nuevoCliente);
        Cliente clienteEncontrado = repo.buscarPorId("id-1");

        // Assert (Afirmar)
        assertNotNull(clienteEncontrado);
        assertEquals("Juan Perez", clienteEncontrado.getNombre());
    }
}