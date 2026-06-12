package com.example.demo.controller;

import com.example.demo.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String usuario,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int edad
    ) {

        Usuario nuevo = new Usuario(usuario, email, password, edad);
        usuarios.add(nuevo);

        return "Usuario creado: " + usuario;
    }

    @PostMapping("/login")
public String login(
        @RequestParam String usuario,
        @RequestParam String password
) {

    for (Usuario u : usuarios) {
        if (u.getUsuario().equals(usuario) &&
            u.getPassword().equals(password)) {

            return "redirect:/dashboard.html";
        }
    }

    return "redirect:/login.html?error=true";
}

}

