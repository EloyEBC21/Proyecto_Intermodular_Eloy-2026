package com.example.demo.model; // Ajusta según tu paquete

public class Producto {
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria; // "Entrante", "Principal", etc.

    public Producto(String nombre, String descripcion, double precio, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
}