/* Si el usuario aclara que el mensaje es en Java, los nombres de variables y comentarios deben estar en español. */

package com.example.demo.model;

import java.time.LocalDateTime;

public class Mensaje {
    private String emisor;
    private String contenido;
    private LocalDateTime fechaEnvio;

    public Mensaje() {
        this.fechaEnvio = LocalDateTime.now();
    }

    public Mensaje(String emisor, String contenido) {
        this.emisor = emisor;
        this.contenido = contenido;
        this.fechaEnvio = LocalDateTime.now();
    }

    /* Getters y setters */
    public String getEmisor() { return emisor; }
    public void setEmisor(String emisor) { this.emisor = emisor; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}