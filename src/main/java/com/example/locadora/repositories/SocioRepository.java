package com.example.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.locadora.domain.Socio;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SocioRepository extends JpaRepository<Socio, Long>{
    boolean existsByNumeroInscricao(int numeroInscricao);

    // listar socios que possuem menos de 3 dependentes ativos
    @Query("SELECT s FROM Socio s WHERE (SELECT COUNT(d) FROM Dependente d WHERE d.socio = s AND d.ativo = TRUE) < 3")
    List<Socio> listarSociosComMenosDe3DependentesAtivos();


}
