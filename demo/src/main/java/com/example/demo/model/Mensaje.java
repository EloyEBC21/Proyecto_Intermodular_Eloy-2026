package com.example.demo.model;

import jakarta.persistence.*; // IMPORTANTE: Estas son las anotaciones de JPA
import java.time.LocalDateTime;

@Entity // Indica a Spring que esto es una tabla de BD
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String emisor;
    private String contenido;
    private LocalDateTime fechaEnvio;

    @ManyToOne // Relación: Muchos mensajes pueden pertenecer a una partida
    @JoinColumn(name = "partida_id")
    private Partida partida;

    public Mensaje() { this.fechaEnvio = LocalDateTime.now(); }

    /* Getters y setters (Añade también los de 'partida') */
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public Partida getPartida() { return partida; }
    public void setPartida(Partida partida) { this.partida = partida; }
}