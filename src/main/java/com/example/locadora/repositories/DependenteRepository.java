package com.example.locadora.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.locadora.domain.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente,Long> {
    boolean existsByNumeroInscricao(int numeroInscricao);

}
