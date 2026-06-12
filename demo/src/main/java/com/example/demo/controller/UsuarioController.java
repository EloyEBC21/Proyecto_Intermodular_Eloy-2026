package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String usuario,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int edad
    ) {

        return "Usuario recibido: " + usuario +
               ", email: " + email +
               ", edad: " + edad;
    }

}