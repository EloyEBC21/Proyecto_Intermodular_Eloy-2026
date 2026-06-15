package com.example.demo.controller;

import com.example.demo.model.Partida;
import com.example.demo.model.Usuario;

import org.springframework.stereotype.Controller;
/* ESTA ES LA LÍNEA QUE TE FALTA: */
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioController {

    private List<Usuario> usuarios = new ArrayList<>();

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model modelo) {
        /* Creamos una lista de Java normal y corriente */
        List<Partida> lista = new ArrayList<>();

        /* Metemos las 4 partidas a mano en la lista */
        lista.add(new Partida("Noche de D&D: La Mina Perdida", "Dungeons & Dragons 5e", "5 Jun - 19:00",
                "Game & Grill - Local", 4, 6));
        lista.add(new Partida("Torneo Catan", "Los Colonos de Catán", "7 Jun - 18:30", "Game & Grill - Local", 8, 12));
        lista.add(new Partida("Magic: The Gathering Commander", "Magic MTG", "10 Jun - 20:00", "Game & Grill - Local",
                6, 8));
        lista.add(new Partida("Mesa libre: Juegos variados", "Varios juegos disponibles", "12 Jun - 17:00",
                "Game & Grill - Local", 3, 10));

        /* Se la enviamos al HTML */
        modelo.addAttribute("listaPartidas", lista);

        return "dashboard";
    }

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String usuario,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int edad) {

        Usuario nuevo = new Usuario(usuario, email, password, edad);
        usuarios.add(nuevo);

        return "redirect:/index.html";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String usuario,
            @RequestParam String password) {

        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) &&
                    u.getPassword().equals(password)) {

                return "redirect:/dashboard.html";
            }
        }

        return "redirect:/index.html?error=true";
    }

}
