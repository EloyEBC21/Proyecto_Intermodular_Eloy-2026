package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByPartidaId(Long partidaId);
}
