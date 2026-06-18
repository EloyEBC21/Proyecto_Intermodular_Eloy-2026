package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // JPA necesita un ID para identificar al usuario
    
    private String usuario;
    private String email;
    private String password;
    private int edad;
    private String juegos;

    // 1. Constructor vacío (Obligatorio para JPA)
    public Usuario() {
    }

    // 2. Constructor con parámetros
    public Usuario(String usuario, String email, String password, int edad, String juegos) {
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.edad = edad;
        this.juegos = juegos;
    }

    public void setId(Long id) { this.id = id; }

    // Getters y Setters
    public Long getId() { return id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getJuegos() { return juegos; }
    public void setJuegos(String juegos) { this.juegos = juegos; }
}