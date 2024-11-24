package com.example.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.locadora.domain.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long>{
    boolean existsByNumeroInscricao(int numeroInscricao);
}
