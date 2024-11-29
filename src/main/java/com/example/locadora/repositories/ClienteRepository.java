package com.example.locadora.repositories;

import com.example.locadora.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNumeroInscricao(int numeroInscricao);

    //listar somente os clientes ativos
    List<Cliente> findByAtivoTrue();

}
