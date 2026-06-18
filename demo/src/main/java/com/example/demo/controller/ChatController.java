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
import com.example.demo.model.Usuario;
import com.example.demo.repository.MensajeRepository;
import com.example.demo.repository.PartidaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatController {
    private final MensajeRepository mensajeRepository;
    private final PartidaRepository partidaRepository;

    public ChatController(MensajeRepository mr, PartidaRepository pr) {
        this.mensajeRepository = mr;
        this.partidaRepository = pr;
    }

    @GetMapping("/partidas/{id}/chat")
    public String mostrarChat(@PathVariable Long id, HttpSession sesion, Model model) {
        if (sesion.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        List<Mensaje> mensajes = mensajeRepository.findByPartidaId(id);
        model.addAttribute("partida", partida);
        model.addAttribute("mensajes", mensajes);
        return "chat-partida";
    }

    @PostMapping("/partidas/{id}/chat/enviar")
    public String enviarMensaje(@PathVariable Long id,
            @RequestParam String contenido,
            HttpSession sesion) { // Ya no hace falta el @RequestParam String emisor

        if (sesion.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        // Recuperamos el nombre del usuario directamente de la sesión!
        Usuario usuarioLogueado = (Usuario) sesion.getAttribute("usuarioLogueado");
        String emisor = usuarioLogueado.getUsuario();

        Partida partida = partidaRepository.findById(id).orElseThrow();
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(contenido);
        mensaje.setEmisor(emisor);
        mensaje.setPartida(partida);
        mensajeRepository.save(mensaje);

        return "redirect:/partidas/" + id + "/chat";
    }
}