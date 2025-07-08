package com.fidelidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate; // Asegúrate de tener esta importación
import java.util.List;

class FidelidadServiceTest {

    private FidelidadService servicio;
    private ClienteRepository clienteRepo;
    private CompraRepository compraRepo;

    @BeforeEach
    void setUp() {
        clienteRepo = new ClienteRepository();
        compraRepo = new CompraRepository(); // Crear instancia
        servicio = new FidelidadService(clienteRepo, compraRepo); // Inyectar ambos
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
        servicio.registrarCompra("id-1", 1500.0, LocalDate.now());

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
        servicio.registrarCompra("id-2", 50000.0, LocalDate.now());

        // Assert
        Cliente clienteActualizado = clienteRepo.buscarPorId("id-2");
        assertEquals(Nivel.PLATA, clienteActualizado.getNivel());
    }

    @Test
    void clienteDeNivelSuperiorDebeGanarPuntosConMultiplicador() {
        // Arrange
        Cliente clientePlata = new Cliente("id-3", "Diana", "diana@mail.com");
        clientePlata.setNivel(Nivel.PLATA); 
        clientePlata.setPuntos(500); // puntos para ser plata
        clienteRepo.guardar(clientePlata);

        // Act: compra de $1000, puntos base = 10, multiplicador plata (1.2) -> 12 puntos.
        servicio.registrarCompra("id-3", 1000.0, LocalDate.now());
        

        // Assert
        Cliente clienteActualizado = clienteRepo.buscarPorId("id-3");
        // puntos totales = 500 (iniciales) + 12 (ganados) = 512
        assertEquals(512, clienteActualizado.getPuntos());
    }



    @Test
    void alRealizarTresComprasEnElMismoDia_seAplicaBonoDeRacha() {
        // Arrange
        Cliente cliente = new Cliente("id-4", "Sofia", "sofia@mail.com");
        clienteRepo.guardar(cliente);
        LocalDate hoy = LocalDate.now();

        // Act
        servicio.registrarCompra("id-4", 1000.0, hoy); // Compra 1: 10 puntos
        servicio.registrarCompra("id-4", 1000.0, hoy); // Compra 2: 10 puntos
        servicio.registrarCompra("id-4", 1000.0, hoy); // Compra 3: 10 puntos + 10 de bono

        // Assert
        Cliente clienteActualizado = clienteRepo.buscarPorId("id-4");
        assertEquals(40, clienteActualizado.getPuntos());
    }

    
    @Test
    void alRegistrarCompra_seDebeGuardarEnElHistorial() {
        // Arrange
        Cliente cliente = new Cliente("id-1", "Ana", "ana@mail.com");
        clienteRepo.guardar(cliente);
        LocalDate hoy = LocalDate.now();

        // Act
        servicio.registrarCompra("id-1", 2000.0, hoy);

        // Assert
        // Suponemos que existirá un CompraRepository inyectado en el servicio
        // y que podemos acceder a él para verificar (o tener un método en el servicio).
        // Por simplicidad, asumiremos que el servicio nos da el historial.
        List<Compra> historial = servicio.obtenerComprasCliente("id-1");
        assertEquals(1, historial.size());
        assertEquals(2000.0, historial.get(0).getMonto());
    }

}