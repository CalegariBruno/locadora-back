package com.example.locadora.repositories;

import com.example.locadora.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TituloRepository extends JpaRepository<Titulo,Long> {
}
