package com.fidelidad;

import java.util.HashMap;
import java.util.Map;

public class ClienteRepository {
    // Usamos un mapa para simular una base de datos en memoria
    private final Map<String, Cliente> clientes = new HashMap<>();

    public void guardar(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    public Cliente buscarPorId(String id) {
        return clientes.get(id);
    }
}