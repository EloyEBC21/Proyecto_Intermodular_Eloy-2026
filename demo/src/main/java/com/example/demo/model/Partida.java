package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String juego;
    private String fecha;
    private String lugar;
    private int jugadoresApuntados;
    private int jugadoresMax;

    // Constructor vacío (OBLIGATORIO para JPA)
    public Partida() {}

    public Partida(String titulo, String juego, String fecha, String lugar,
                   int jugadoresApuntados, int jugadoresMax) {
        this.titulo = titulo;
        this.juego = juego;
        this.fecha = fecha;
        this.lugar = lugar;
        this.jugadoresApuntados = jugadoresApuntados;
        this.jugadoresMax = jugadoresMax;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public int getJugadoresApuntados() { return jugadoresApuntados; }
    public void setJugadoresApuntados(int jugadoresApuntados) { this.jugadoresApuntados = jugadoresApuntados; }

    public int getJugadoresMax() { return jugadoresMax; }
    public void setJugadoresMax(int jugadoresMax) { this.jugadoresMax = jugadoresMax; }
}