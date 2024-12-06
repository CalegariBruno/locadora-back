package com.example.locadora.repositories;

import com.example.locadora.domain.Titulo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TituloRepository extends JpaRepository<Titulo,Long> {
<<<<<<< HEAD
    Optional<Titulo> findByNome(String nome);
=======

    // consultar por nome
    List<Titulo> findByNome(String nome);

    // consultar por categoria
    List<Titulo> findByCategoria(String categoria);

    // consultar por ator
    List<Titulo> findByAtores_Nome(String ator);

>>>>>>> e7a7343a370c37e1c0b55efc6d6cfaf18001b4ea
}
