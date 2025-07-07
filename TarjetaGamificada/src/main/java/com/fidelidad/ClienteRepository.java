package com.fidelidad;

import java.util.HashMap;
import java.util.Map;

public class ClienteRepository {
    // mapa para datos en memoria
    private final Map<String, Cliente> clientes = new HashMap<>();

    public void guardar(Cliente cliente) {
        
        if (cliente.getCorreo() == null || !cliente.getCorreo().contains("@")) {
            throw new IllegalArgumentException("El formato del correo es inválido.");
        }
        clientes.put(cliente.getId(), cliente);
    }

    public Cliente buscarPorId(String id) {
        return clientes.get(id);
    }
}