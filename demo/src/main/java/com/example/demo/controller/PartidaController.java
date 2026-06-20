package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import com.example.demo.model.Partida;
import com.example.demo.repository.PartidaRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PartidaController {

    @Autowired
    private PartidaRepository partidaRepository;

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        // Obtenemos las partidas reales desde MySQL
        modelo.addAttribute("listaPartidas", partidaRepository.findAll());
        return "dashboard";
    }

    @GetMapping("/formulario-partida")
    public String mostrarFormulario(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "formulario-partida";
    }

    @GetMapping("/partidas/crear")
    public String mostrarFormulario(HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null) return "redirect:/";
        
        modelo.addAttribute("partida", new Partida());
        return "formulario-partida";
    }

    @PostMapping("/partidas/crear")
    public String guardarPartida(@ModelAttribute Partida partidaRecibida, HttpSession sesion) {
        if (sesion.getAttribute("usuarioLogueado") == null) return "redirect:/";
        
        // Guardado persistente en MySQL
        partidaRepository.save(partidaRecibida);
        return "redirect:/dashboard";
    }
}