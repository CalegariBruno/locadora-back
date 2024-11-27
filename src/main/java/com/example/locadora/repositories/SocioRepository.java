package com.example.locadora.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.locadora.domain.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long>{
    @Query("SELECT s.id, s.nome FROM socio LEFT JOIN dependente d ON s.id = d.socio_id AND d.ativo = true GROUP BY s.id, s.nome HAVING COUNT(d.id) < 3")
    List<Socio> findSociosWithLessThan3ActiveDependentes();
}
