package com.fidelidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompraRepository {
    private final List<Compra> compras = new ArrayList<>();

    public void guardar(Compra compra) {
        compras.add(compra);
    }

    public List<Compra> buscarPorIdCliente(String idCliente) {
        return compras.stream()
            .filter(c -> c.getIdCliente().equals(idCliente))
            .collect(Collectors.toList());
    }

    public void eliminar(String idCompra) {
        compras.removeIf(compra -> compra.getIdCompra().equals(idCompra));
    }

}