package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/perfil")
    public String verMiPerfil(HttpSession sesion, Model modelo) {
        Usuario user = (Usuario) sesion.getAttribute("usuarioLogueado");
        if (user == null)
            return "redirect:/";
        modelo.addAttribute("usuarioLogueado", user);
        return "perfil";
    }

    @GetMapping("/usuarios")
    public String explorarPerfiles(HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        // Obtenemos los usuarios reales de la BBDD
        modelo.addAttribute("listaUsuarios", usuarioRepository.findAll());
        return "explorar-usuarios";
    }

    @GetMapping("/menu")
    public String mostrarMenu(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        // Obtenemos los productos desde MySQL
        List<Producto> todosLosProductos = productoRepository.findAll();

        Map<String, List<Producto>> menuAgrupado = todosLosProductos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria));

        model.addAttribute("menu", menuAgrupado);

        // Lógica del carrito
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        double totalCarrito = (carrito != null) ? carrito.stream().mapToDouble(Producto::getPrecio).sum() : 0.0;
        model.addAttribute("totalCarrito", totalCarrito);

        return "menu";
    }

    @GetMapping("/menu/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        // Esto borra el producto de la base de datos por su identificador único
        productoRepository.deleteById(id);

        // Redirigimos de vuelta al menú para que la lista se refresque
        return "redirect:/menu";
    }

    @GetMapping("/usuarios/{id}")
    public String verPerfilUsuario(@PathVariable("id") Long id, HttpSession sesion, Model modelo) {
        if (sesion.getAttribute("usuarioLogueado") == null)
            return "redirect:/";

        // Buscamos al usuario por ID en MySQL
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    modelo.addAttribute("usuario", usuario);
                    return "perfil-usuario";
                })
                .orElse("redirect:/usuarios");
    }

    @GetMapping("/register")
    public String mostrarRegister() {
        return "register";
    }

    @GetMapping("/pago")
    public String mostrarPaginaPago(HttpSession session, Model model) {
        // Si no hay carrito, quizás no tenga sentido pagar
        if (session.getAttribute("carrito") == null) {
            return "redirect:/menu";
        }
        return "pago"; // Esto buscará pago.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String password,
            HttpSession sesion,
            RedirectAttributes redirectAttributes) { // <--- Inyectamos esto

        Usuario u = usuarioRepository.findByUsuarioAndPassword(usuario, password);

        if (u != null) {
            sesion.setAttribute("usuarioLogueado", u);
            return "redirect:/dashboard";
        }

        // En lugar de ?error=true, pasamos un mensaje flash
        redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
        return "redirect:/"; // Regresamos al inicio
    }

    @PostMapping("/register")
    public String registrar(@RequestParam String usuario, @RequestParam String email,
            @RequestParam String password, @RequestParam int edad,
            @RequestParam(required = false) String juegos,
            RedirectAttributes redirectAttributes) { // Añadimos esto
        try {
            Usuario nuevo = new Usuario(usuario, email, password, edad, juegos);
            usuarioRepository.save(nuevo);
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            // Si hay error de duplicidad, enviamos el mensaje al HTML
            redirectAttributes.addFlashAttribute("error",
                    "El usuario '" + usuario + "' ya existe. Por favor, elige otro.");
            return "redirect:/register"; // Redirigimos de vuelta al formulario
        }
    }

    @PostMapping("/procesar-pago")
    public String procesarPago(HttpSession session) {
        // Vaciamos el carrito de la sesión al realizar el pago
        session.removeAttribute("carrito");

        // Redirigimos al dashboard tras el pago
        return "redirect:/dashboard";
    }

    @PostMapping("/menu/agregar")
    public String agregarAlCarrito(@RequestParam String nombre,
            @RequestParam double precio,
            HttpSession session) {

        // 1. Obtener o crear el carrito de la sesión
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new java.util.ArrayList<>();
        }

        // 2. Buscar el producto (o crearlo temporalmente) y añadirlo
        // Aquí puedes buscar el producto completo por nombre si lo necesitas,
        // pero para empezar, crearemos un objeto simple:
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        carrito.add(p);

        // 3. Guardar el carrito actualizado en la sesión
        session.setAttribute("carrito", carrito);

        return "redirect:/menu";
    }

    @PostMapping("/menu/eliminar")
    public String eliminarDelCarrito(@RequestParam int indice, HttpSession sesion) {
        List<Producto> carrito = (List<Producto>) sesion.getAttribute("carrito");

        if (carrito != null && indice >= 0 && indice < carrito.size()) {
            carrito.remove(indice);
            sesion.setAttribute("carrito", carrito); // Actualizamos la sesión
        }

        return "redirect:/menu";
    }

}