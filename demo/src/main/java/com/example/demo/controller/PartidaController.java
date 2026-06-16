/* Si el usuario aclara que el mensaje es en Java, los nombres de variables y comentarios deben estar en español. */

package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.Partida;

@Controller
public class PartidaController {

    /* Pequeña base de datos temporal en memoria para guardar tus eventos */
    private final List<Partida> listaPartidasGlobal = new ArrayList<>();

    /* El constructor añade los 4 casos de prueba iniciales */
    public PartidaController() {
        listaPartidasGlobal.add(new Partida("Noche de D&D: La Mina Perdida", "Dungeons & Dragons 5e", LocalDate.parse("2026-06-05"), "Game & Grill - Local", 4, 6));
        listaPartidasGlobal.add(new Partida("Torneo Catan", "Los Colonos de Catán", LocalDate.parse("2026-06-07"), "Game & Grill - Local", 8, 12));
        listaPartidasGlobal.add(new Partida("Magic: The Gathering Commander", "Magic MTG", LocalDate.parse("2026-06-10"), "Game & Grill - Local", 6, 8));
        listaPartidasGlobal.add(new Partida("Mesa libre: Juegos variados", "Varios juegos disponibles", LocalDate.parse("2026-06-12"), "Game & Grill - Local", 3, 10));
    }

    /* Único método encargado de gestionar la pantalla del Dashboard */
    @GetMapping("/dashboard")
    public String mostrarDashboard(Model modelo) {
        modelo.addAttribute("listaPartidas", listaPartidasGlobal);
        return "dashboard";
    }

    /* Método encargado de preparar y pintar el formulario */
    @GetMapping("/partidas/crear")
    public String mostrarFormulario(Model modelo) {
        modelo.addAttribute("partida", new Partida());
        return "formulario-partida";
    }

    /* Método encargado de procesar el formulario sin errores de desbordamiento */
    @PostMapping("/partidas/crear")
    public String guardarPartida(@ModelAttribute Partida partidaRecibida) {
        listaPartidasGlobal.add(partidaRecibida);
        return "redirect:/dashboard";
    }
}