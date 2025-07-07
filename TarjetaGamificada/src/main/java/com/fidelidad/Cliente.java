package com.fidelidad;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    // ... otros atributos vendrán después

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }
    
    // Getters necesarios para la prueba
    public String getId() { return id; }
    public String getNombre() { return nombre; }
}
