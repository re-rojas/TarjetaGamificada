package com.fidelidad;

import java.time.LocalDate;

public class Compra {
    private String idCompra;
    private String idCliente;
    private double monto;
    private LocalDate fecha;

    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Añade Getters para todos los atributos
    public String getIdCompra() { return idCompra; }
    public String getIdCliente() { return idCliente; }
    public double getMonto() { return monto; }
    public LocalDate getFecha() { return fecha; }
}