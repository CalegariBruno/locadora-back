package com.example.locadora.repositories;

import com.example.locadora.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.locadora.domain.Locacao;

import java.util.Optional;

public interface LocacaoRepository extends JpaRepository<Locacao,Long> {

    // Query para buscar locações que não foram pagas
    Optional<Locacao> findByItemAndPagoFalse(Item item);

}
