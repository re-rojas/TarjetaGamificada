package com.fidelidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FidelidadServiceTest {

    private FidelidadService servicio;
    private ClienteRepository clienteRepo;

    @BeforeEach // antes de cada test
    void setUp() {
        clienteRepo = new ClienteRepository();
        servicio = new FidelidadService(clienteRepo); // para que necesite el repo
    }

    @Test
    void testCalcularPuntosBase() {
        assertEquals(0, servicio.calcularPuntosBase(99.99));
        assertEquals(1, servicio.calcularPuntosBase(100.0));
        assertEquals(1, servicio.calcularPuntosBase(199.0));
        assertEquals(10, servicio.calcularPuntosBase(1000.0));
    }

    @Test
    void alRegistrarCompra_seDebenSumarPuntosAlCliente() {
        // Arrange
        Cliente cliente = new Cliente("id-1", "Ana", "ana@mail.com");
        clienteRepo.guardar(cliente); // guardar cliente con 0 puntos

        // Act: registrar compra de $1500, que debería dar 15 puntos.
        servicio.registrarCompra("id-1", 1500.0);

        // Assert
        Cliente clienteActualizado = clienteRepo.buscarPorId("id-1");
        assertEquals(15, clienteActualizado.getPuntos());
    }

    @Test
    void alAcumularSuficientesPuntos_clienteDebeSubirDeNivel() {
        // Arrange
        Cliente cliente = new Cliente("id-2", "Carlos", "carlos@mail.com");
        clienteRepo.guardar(cliente); // cliente nuevo es nivel bronce

        // Act: compra de 50000 -> 500 puntos
        servicio.registrarCompra("id-2", 50000.0);

        // Assert
        Cliente clienteActualizado = clienteRepo.buscarPorId("id-2");
        assertEquals(Nivel.PLATA, clienteActualizado.getNivel());
    }
}