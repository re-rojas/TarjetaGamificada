package com.fidelidad;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FidelidadService {

    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;

    public FidelidadService(ClienteRepository clienteRepository, CompraRepository compraRepository) {
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
    }

    public int calcularPuntosBase(double monto) {
        return (int) (monto / 100);
    }

    public void registrarCompra(String clienteId, double monto, LocalDate fechaCompra) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null) return;

        int bonoRacha = gestionarRachaYObtenerBono(cliente, fechaCompra);

        int puntosBase = this.calcularPuntosBase(monto);
        double multiplicador = cliente.getNivel().getMultiplicador();
        int puntosGanados = (int) Math.round(puntosBase * multiplicador) + bonoRacha;

        cliente.setPuntos(cliente.getPuntos() + puntosGanados);
        cliente.setUltimaFechaCompra(fechaCompra);
        actualizarNivel(cliente);
        //guardar compra en historial:
        String idCompra = UUID.randomUUID().toString();
        Compra nuevaCompra = new Compra(idCompra, clienteId, monto, fechaCompra);
        compraRepository.guardar(nuevaCompra);
    }

    private int gestionarRachaYObtenerBono(Cliente cliente, LocalDate fechaCompra) {
        if (fechaCompra.equals(cliente.getUltimaFechaCompra())) {
            cliente.setStreakDias(cliente.getStreakDias() + 1);
        } else {
            cliente.setStreakDias(1);
        }

        if (cliente.getStreakDias() == 3) {
            cliente.setStreakDias(0);
            return 10; // devuelve pts de bono
        }
        return 0; // cuando no hay bono
    }

    private void actualizarNivel(Cliente cliente) {
        int puntos = cliente.getPuntos();
        if (puntos >= 3000) {
            cliente.setNivel(Nivel.PLATINO);
        } else if (puntos >= 1500) {
            cliente.setNivel(Nivel.ORO);
        } else if (puntos >= 500) {
            cliente.setNivel(Nivel.PLATA);
        } else {
            cliente.setNivel(Nivel.BRONCE);
        }
    }

    public List<Compra> obtenerComprasCliente(String idCliente) {
        return compraRepository.buscarPorIdCliente(idCliente);
    }
}