package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Mensaje;
import com.example.demo.model.Partida;
import com.example.demo.repository.MensajeRepository;
import com.example.demo.repository.PartidaRepository;

@Controller
public class ChatController {
    private final MensajeRepository mensajeRepository;
    private final PartidaRepository partidaRepository;

    public ChatController(MensajeRepository mr, PartidaRepository pr) {
        this.mensajeRepository = mr;
        this.partidaRepository = pr;
    }

    @GetMapping("/partidas/{id}/chat")
    public String mostrarChat(@PathVariable Long id, Model model) {
        // 1. Cargamos la partida
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        // 2. Cargamos todos los mensajes de esta partida
        List<Mensaje> mensajes = mensajeRepository.findByPartidaId(id);

        // 3. Pasamos los datos a la vista
        model.addAttribute("partida", partida);
        model.addAttribute("mensajes", mensajes);

        return "chat-partida"; // El nombre del archivo HTML
    }

    @PostMapping("/partidas/{id}/chat/enviar")
    public String enviarMensaje(@PathVariable Long id,
            @RequestParam String contenido,
            @RequestParam String emisor) { // Recogemos el emisor del input

        Partida partida = partidaRepository.findById(id).orElseThrow();

        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setEmisor(emisor);
        mensaje.setPartida(partida);

        mensajeRepository.save(mensaje);

        return "redirect:/partidas/" + id + "/chat";
    }
}