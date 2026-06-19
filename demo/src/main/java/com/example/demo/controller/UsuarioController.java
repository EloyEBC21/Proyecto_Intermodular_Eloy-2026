package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Usuario;

import jakarta.servlet.http.HttpSession; // Importante para la sesión

@Controller
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioController() {
        // Datos de prueba iniciales
        Usuario u1 = new Usuario("Guillermo", "guille@mail.com", "1234", 28,
                "Dungeons & Dragons, Magic: The Gathering");
        u1.setId(1L);
        usuarios.add(u1);
        // ... (resto de usuarios igual)
    }

    // LOGIN: Al loguearse correctamente, guardamos el usuario en la sesión
    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String password, HttpSession sesion) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                sesion.setAttribute("usuarioLogueado", u); // Guardamos el usuario en la sesión
                return "redirect:/dashboard";
            }
        }
        return "redirect:/?error=true";
    }

    // PERFIL: Solo permite entrar si hay un usuario en la sesión
    @GetMapping("/perfil")
    public String verMiPerfil(HttpSession sesion, Model modelo) {
        Usuario user = (Usuario) sesion.getAttribute("usuarioLogueado");
        if (user == null) {
            return "redirect:/"; // Si no está logueado, lo mandamos al inicio
        }
        modelo.addAttribute("usuarioLogueado", user);
        return "perfil";
    }

    // EXPLORAR: Protegido
    @GetMapping("/usuarios")
    public String explorarPerfiles(HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }
        modelo.addAttribute("listaUsuarios", usuarios);
        return "explorar-usuarios";
    }

    // PERFIL INDIVIDUAL: Protegido
    @GetMapping("/usuarios/{id}")
    public String verPerfilUsuario(@PathVariable("id") Long id, HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        Usuario usuarioEncontrado = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (usuarioEncontrado == null) {
            return "redirect:/usuarios";
        }

        modelo.addAttribute("usuario", usuarioEncontrado);
        return "perfil-usuario";
    }

    @GetMapping("/register")
    public String mostrarRegister() {
        return "register";
    }

    @GetMapping("/menu")
    public String mostrarMenu(HttpSession session, Model model) {
        // 1. Protección de seguridad
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        // 2. Aquí cargarás tus productos más adelante
        // model.addAttribute("productos", productoService.findAll());

        return "menu"; // Debe ser el nombre del archivo html
    }

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String usuario,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int edad,
            @RequestParam(required = false) String juegos) { // Añadimos required=false por si no escriben nada

        Usuario nuevo = new Usuario(usuario, email, password, edad, juegos);

        // ¡IMPORTANTE! Debes guardar los juegos en el usuario
        // Asegúrate de tener el método setJuegos(String juegos) en tu clase Usuario
        nuevo.setJuegos(juegos);

        // Asignación de ID autoincremental
        long maxId = usuarios.stream()
                .mapToLong(Usuario::getId)
                .max()
                .orElse(0L);
        nuevo.setId(maxId + 1);

        usuarios.add(nuevo);

        return "redirect:/";
    }
}