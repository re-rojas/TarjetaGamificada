package com.fidelidad;

import java.time.LocalDate;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int streakDias;
    private LocalDate ultimaFechaCompra;

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.streakDias = 0;
        this.ultimaFechaCompra = null;
    }
    
    // getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() {
        return correo;
    }
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public int getStreakDias() { 
        return streakDias; 
    }
    public void setStreakDias(int streakDias) { 
        this.streakDias = streakDias; 
    }
    public LocalDate getUltimaFechaCompra() { 
        return ultimaFechaCompra; 
    }
    public void setUltimaFechaCompra(LocalDate ultimaFechaCompra) { 
        this.ultimaFechaCompra = ultimaFechaCompra; 
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
