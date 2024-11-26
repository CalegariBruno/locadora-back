package com.example.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.locadora.domain.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao,Long> {    
}
