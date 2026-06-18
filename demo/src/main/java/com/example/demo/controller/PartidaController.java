package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

import com.example.demo.model.Partida;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PartidaController {

    // Base de datos temporal en memoria para almacenar las partidas del negocio
    private final List<Partida> listaPartidasGlobal = new ArrayList<>();

    // El constructor inicializa los cuatro registros de simulación con el nuevo
    // formato horario
    public PartidaController() {
        listaPartidasGlobal.add(new Partida("Noche de D&D: La Mina Perdida", "Dungeons & Dragons 5e",
                LocalDateTime.of(2026, 6, 5, 18, 30), "Game & Grill - Local", 4, 6));

        listaPartidasGlobal.add(new Partida("Torneo Catan", "Los Colonos de Catán",
                LocalDateTime.of(2026, 6, 7, 17, 0), "Game & Grill - Local", 8, 12));

        listaPartidasGlobal.add(new Partida("Magic: The Gathering Commander", "Magic MTG",
                LocalDateTime.of(2026, 6, 10, 19, 15), "Game & Grill - Local", 6, 8));

        listaPartidasGlobal.add(new Partida("Mesa libre: Juegos variados", "Varios juegos disponibles",
                LocalDateTime.of(2026, 6, 12, 20, 0), "Game & Grill - Local", 3, 10));
    }

    // Procesa la solicitud GET para renderizar la lista completa de partidas en la
    // interfaz principal
    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        modelo.addAttribute("listaPartidas", listaPartidasGlobal);
        return "dashboard";
    }

    @GetMapping("/formulario-partida")
    public String mostrarFormulario(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "formulario-partida";
    }

    // Prepara el entorno enviando una instancia limpia del modelo a la vista del
    // formulario
    @GetMapping("/partidas/crear")
    public String mostrarFormulario(HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null) return "redirect:/";
        
        modelo.addAttribute("partida", new Partida());
        return "formulario-partida";
    }

    // Captura los datos enviados desde el formulario HTML y añade la nueva partida
    // a la lista global
    @PostMapping("/partidas/crear")
    public String guardarPartida(@ModelAttribute Partida partidaRecibida, HttpSession sesion) {
        if (sesion.getAttribute("usuarioLogueado") == null) return "redirect:/";
        
        listaPartidasGlobal.add(partidaRecibida);
        return "redirect:/dashboard";
    }
}