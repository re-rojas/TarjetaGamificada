package com.fidelidad;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
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

}
