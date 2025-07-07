package com.fidelidad;

public class FidelidadService {

    private final ClienteRepository clienteRepository;

    public FidelidadService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public int calcularPuntosBase(double monto) {
        return (int) (monto / 100);
    }

    public void registrarCompra(String clienteId, double monto) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente != null) {
            int puntosGanados = this.calcularPuntosBase(monto);
            cliente.setPuntos(cliente.getPuntos() + puntosGanados);

            actualizarNivel(cliente);
        }
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
}