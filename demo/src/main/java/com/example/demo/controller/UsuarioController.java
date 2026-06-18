/* Si el usuario aclara que el mensaje es en Java, los nombres de variables y comentarios deben estar en español. */

package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Usuario;

@Controller
public class UsuarioController {

    private List<Usuario> usuarios = new ArrayList<>();

    /*
     * NUEVA RUTA BASE: Atiende la petición de la raíz para servir el index
     * correctamente
     */
    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
    }

    /*
     * NUEVA RUTA REGISTER: Atiende la petición para abrir la pantalla de registro
     */
    @GetMapping("/register")
    public String mostrarRegister() {
        return "register";
    }

    /* 📍 NUEVA RUTA: Muestra el perfil del usuario que está navegando */
    @GetMapping("/perfil")
    public String verMiPerfil(Model modelo) {
        /*
         * Si la lista no está vacía, pasamos el primer usuario registrado como
         * simulación
         */
        if (!usuarios.isEmpty()) {
            modelo.addAttribute("usuarioLogueado", usuarios.get(0));
        } else {
            /*
             * Si no hay nadie registrado aún, mandamos un usuario ficticio para que la
             * página no falle
             */
            modelo.addAttribute("usuarioLogueado", new Usuario("Invitado", "invitado@mail.com", "1234", 18));
        }
        return "perfil";
    }

    /*
     * 📍 NUEVA RUTA: Muestra el explorador con la lista de todos los usuarios
     * registrados
     */
    @GetMapping("/usuarios")
    public String explorarPerfiles(Model modelo) {
        System.out.println("DEBUG: Entrando en /usuarios");
        modelo.addAttribute("listaUsuarios", usuarios);
        return "explorar-usuarios"; 
    }


    /* 📍 NUEVA RUTA: Muestra el perfil de un usuario específico usando su ID */
    @GetMapping("/usuarios/{id}")
    public String verPerfilUsuario(@PathVariable("id") Long id, Model modelo) {
        // Aquí buscarías al usuario en la BD.
        // Como ahora usas una lista en memoria, puedes filtrarla:
        Usuario usuarioEncontrado = usuarios.stream()
                .filter(u -> u.getId().equals(id)) // Requiere que Usuario tenga método getId()
                .findFirst()
                .orElse(null);

        modelo.addAttribute("usuario", usuarioEncontrado);
        return "perfil-usuario"; // Debes crear este archivo HTML
    }

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String usuario,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int edad) {

        Usuario nuevo = new Usuario(usuario, email, password, edad);
        usuarios.add(nuevo);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String usuario,
            @RequestParam String password) {

        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                return "redirect:/dashboard";
            }
        }

        return "redirect:/?error=true";
    }
}