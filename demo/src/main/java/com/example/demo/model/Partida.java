package com.example.demo.model;

public class Partida {
    private String titulo;
    private String juego;
    private String fecha; /* Usamos texto plano para no complicar con fechas */
    private String lugar;
    private int jugadoresApuntados;
    private int jugadoresMax;

    /* Constructor simple */
    public Partida(String titulo, String juego, String fecha, String lugar, int jugadoresApuntados, int jugadoresMax) {
        this.titulo = titulo;
        this.juego = juego;
        this.fecha = fecha;
        this.lugar = lugar;
        this.jugadoresApuntados = jugadoresApuntados;
        this.jugadoresMax = jugadoresMax;
    }

    /* Getters necesarios para que el HTML pueda leer los datos */
    public String getTitulo() { return titulo; }
    public String getJuego() { return juego; }
    public String getFecha() { return fecha; }
    public String getLugar() { return lugar; }
    public int getJugadoresApuntados() { return jugadoresApuntados; }
    public int getJugadoresMax() { return jugadoresMax; }
}