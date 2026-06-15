package com.example.demo.controller;

import com.example.demo.model.Partida;
import com.example.demo.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartidaController {

    @Autowired
    private PartidaRepository partidaRepository;

    // 📍 Mostrar formulario
    @GetMapping("/formulario-partida")
    public String mostrarFormulario(Model model) {
        model.addAttribute("partida", new Partida());
        return "formulario-partida";
    }

    // 📍 Guardar partida en H2
    @PostMapping("/partidas/crear")
    public String crearPartida(@ModelAttribute Partida partida) {
        partidaRepository.save(partida);
        return "redirect:/dashboard";
    }

    // 📍 Mostrar dashboard con todas las partidas
    @GetMapping("/partidas")
    public String dashboard(Model model) {
        model.addAttribute("partidas", partidaRepository.findAll());
        return "dashboard";
    }
}