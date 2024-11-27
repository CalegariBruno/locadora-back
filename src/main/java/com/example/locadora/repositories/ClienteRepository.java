package com.example.locadora.repositories;

import com.example.locadora.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNumeroInscricao(int numeroInscricao);
}
