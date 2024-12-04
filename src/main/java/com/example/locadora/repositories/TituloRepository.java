package com.example.locadora.repositories;

import com.example.locadora.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TituloRepository extends JpaRepository<Titulo,Long> {

    // consultar por nome
    List<Titulo> findByNome(String nome);

    // consultar por categoria
    List<Titulo> findByCategoria(String categoria);

    // consultar por ator
    List<Titulo> findByAtores_Nome(String ator);

}
