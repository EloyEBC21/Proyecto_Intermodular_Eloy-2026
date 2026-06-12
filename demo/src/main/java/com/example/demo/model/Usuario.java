package com.example.demo.model;

public class Usuario {

    private String usuario;
    private String email;
    private String password;
    private int edad;

    public Usuario(String usuario, String email, String password, int edad) {
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.edad = edad;
    }

    public String getUsuario() { return usuario; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public int getEdad() { return edad; }
}