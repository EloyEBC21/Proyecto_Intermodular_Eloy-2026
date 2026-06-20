package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;

import jakarta.servlet.http.HttpSession; // Importante para la sesión

@Controller
public class UsuarioController {

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
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/";
        }

        // Supongamos que esta es tu lista original
        List<Producto> todosLosProductos = Arrays.asList(
                new Producto("Nachos Supreme", "Con queso, guacamole y jalapeños", 8.50, "Entrantes"),
                new Producto("Alitas de BBQ", "8 unidades con salsa BBQ", 9.00, "Entrantes"),
                new Producto("Tequeños", "6 unidades con salsa de miel y mostaza", 7.50, "Entrantes"),
                new Producto("Aros de Cebolla", "Crujientes con salsa agridulce", 6.00, "Entrantes"),

                // Ensaladas
                new Producto("Ensalada César", "Pollo, picatostes, parmesano y salsa César", 9.50, "Ensaladas"),
                new Producto("Ensalada Caprese", "Tomate, mozzarella fresca y albahaca", 8.50, "Ensaladas"),

                // Hamburguesas
                new Producto("Hamburguesa Gamer", "Doble carne, bacon, queso y patatas", 12.50, "Hamburguesas"),
                new Producto("Hamburguesa Veggie", "Hamburguesa de garbanzos, aguacate y rúcula", 11.00,
                        "Hamburguesas"),
                new Producto("Hamburguesa Doble Queso", "Carne de ternera con triple queso fundido", 13.00,
                        "Hamburguesas"),

                // Pizzas
                new Producto("Pizza D&D", "Pizza familiar con ingredientes épicos", 15.00, "Pizzas"),
                new Producto("Pizza Margarita", "Tomate, mozzarella y albahaca", 10.00, "Pizzas"),
                new Producto("Pizza Barbacoa", "Carne picada, bacon y salsa barbacoa", 13.50, "Pizzas"),

                // Bocadillos
                new Producto("Bocadillo Serrano", "Jamón serrano, tomate y aceite de oliva", 6.50, "Bocadillos"),
                new Producto("Bocadillo de Pollo", "Pechuga, lechuga, tomate y mayonesa", 7.00, "Bocadillos"),

                // Bebidas
                new Producto("Coca-Cola", "Refresco de cola bien frío", 2.50, "Bebidas"),
                new Producto("Cerveza Artesana", "Cerveza local de estilo IPA", 4.00, "Bebidas"),
                new Producto("Agua Mineral", "Botella de 50cl", 1.50, "Bebidas"),

                // Salsas
                new Producto("Salsa Barbacoa", "Salsa casera ahumada", 0.50, "Salsas"),
                new Producto("Alioli", "Ajo suave y aceite", 0.50, "Salsas"),

                // Postres
                new Producto("Brownie de Chocolate", "Con helado de vainilla y nueces", 5.50, "Postres"),
                new Producto("Tarta de Queso", "Casera con mermelada de frutos rojos", 5.00, "Postres"),
                new Producto("Helado de Vainilla", "Dos bolas de helado artesano", 4.00, "Postres"));

        todosLosProductos.removeIf(Objects::isNull);

        // Agrupamos por categoría usando Java Streams
        Map<String, List<Producto>> menuAgrupado = todosLosProductos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoria));

        model.addAttribute("menu", menuAgrupado);

        // --- NUEVA LÓGICA DE SUMA ---
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        double totalCarrito = 0.0;
        if (carrito != null) {
            // Sumamos los precios de todos los productos del carrito
            totalCarrito = carrito.stream().mapToDouble(Producto::getPrecio).sum();
        }
        model.addAttribute("totalCarrito", totalCarrito);
        // ----------------------------

        return "menu";
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

    @PostMapping("/menu/agregar")
    public String agregarAlCarrito(@RequestParam String nombre,
            @RequestParam double precio,
            HttpSession session) {
        // 1. Obtener o crear la lista del carrito en sesión
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        double totalCarrito = 0.0;
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        // 2. Agregar el producto (asumiendo que Producto tiene nombre y precio)
        carrito.add(new Producto(nombre, null, precio, null));

        // 3. Guardar y redirigir
        session.setAttribute("carrito", carrito);
        return "redirect:/menu"; // Redirección para evitar reenvío de formulario
    }

    @GetMapping("/pago")
    public String mostrarPago(HttpSession session) {
        // Si no hay carrito, no tiene sentido pagar
        if (session.getAttribute("carrito") == null)
            return "redirect:/menu";
        return "pago";
    }

    @PostMapping("/procesar-pago")
    public String procesarPago(HttpSession session) {
        // 1. Limpiamos el carrito, pero MANTENEMOS al usuario en la sesión
        session.removeAttribute("carrito");
        // 2. Redirigimos explícitamente a /dashboard
        System.out.println("¿Existe carrito en sesión tras borrar? " + (session.getAttribute("carrito") != null));
        return "redirect:/dashboard";
    }

}