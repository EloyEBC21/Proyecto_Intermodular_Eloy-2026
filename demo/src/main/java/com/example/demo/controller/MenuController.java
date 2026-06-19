package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MenuController {
    @GetMapping("/menu")
    public String mostrarMenu() {
        return "menu"; // Carga el archivo menu.html
    }
}
