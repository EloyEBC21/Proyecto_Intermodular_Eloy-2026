/* Si el usuario aclara que el mensaje es en Java, los nombres de variables y comentarios deben estar en español. */

package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
     * si alguien navega hacia ella
     */
    @GetMapping("/register")
    public String mostrarRegister() {
        return "register";
    }

    /* * EL MÉTODO mostrarDashboard QUE ESTABA AQUÍ HA SIDO ELIMINADO 
     * Ahora su único dueño es PartidaController.java para evitar duplicados.
     */

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String usuario,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int edad) {

        Usuario nuevo = new Usuario(usuario, email, password, edad);
        usuarios.add(nuevo);

        /*
         * LÍNEA CAMBIADA: Obligamos al navegador a viajar de vuelta a la URL raíz
         * limpiamente
         */
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String usuario,
            @RequestParam String password) {

        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) &&
                    u.getPassword().equals(password)) {

                /*
                 * LÍNEA CAMBIADA: Quitamos el .html para que invoque al
                 * método @GetMapping("/dashboard") del controlador de partidas
                 */
                return "redirect:/dashboard";
            }
        }

        /* LÍNEA CAMBIADA: Quitamos el .html y mandamos el error a la raíz */
        return "redirect:/?error=true";
    }

}