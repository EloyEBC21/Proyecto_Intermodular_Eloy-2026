package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring crea el SQL automáticamente: SELECT * FROM usuarios WHERE usuario = ? AND password = ?
    Usuario findByUsuarioAndPassword(String usuario, String password);
}