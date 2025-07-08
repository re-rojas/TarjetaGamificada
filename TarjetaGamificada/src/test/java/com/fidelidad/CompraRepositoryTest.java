package com.fidelidad;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CompraRepositoryTest {

    @Test
    void alEliminarUnaCompra_yaNoDeberiaEstarEnElRepositorio() {
        // Arrange
        CompraRepository repo = new CompraRepository();
        Compra compra = new Compra("compra-1", "cliente-1", 100.0, LocalDate.now());
        repo.guardar(compra);

        // Act
        repo.eliminar("compra-1");

        // Assert
        assertTrue(repo.buscarPorIdCliente("cliente-1").isEmpty());
    }
}