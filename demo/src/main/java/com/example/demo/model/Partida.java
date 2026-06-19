package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* Simplemente he borrado una libreria que no se usaba pensando que "LocalDateTime" necesitaba la libreria que he borrado */
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "partidas") // Nombre de la tabla en la BD
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String juego;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fecha;
    private String lugar;
    private int jugadoresApuntados;
    private int jugadoresMax;

    @Transient
    private List<Mensaje> chat = new ArrayList<>();

    /* Y sus correspondientes getter y setter */
    public List<Mensaje> getChat() {
        return chat;
    }

    public void setChat(List<Mensaje> chat) {
        this.chat = chat;
    }

    // Constructor vacío (OBLIGATORIO para JPA)
    public Partida() {
    }

    public Partida(String titulo, String juego, LocalDateTime fecha, String lugar,
            int jugadoresApuntados, int jugadoresMax) {
        this.titulo = titulo;
        this.juego = juego;
        this.fecha = fecha;
        this.lugar = lugar;
        this.jugadoresApuntados = jugadoresApuntados;
        this.jugadoresMax = jugadoresMax;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getJugadoresApuntados() {
        return jugadoresApuntados;
    }

    public void setJugadoresApuntados(int jugadoresApuntados) {
        this.jugadoresApuntados = jugadoresApuntados;
    }

    public int getJugadoresMax() {
        return jugadoresMax;
    }

    public void setJugadoresMax(int jugadoresMax) {
        this.jugadoresMax = jugadoresMax;
    }
}