package com.example.locadora.repositories;

import com.example.locadora.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    // Query para buscar itens que não estão locados ou estão locados e já foram pagos
    @Query("SELECT i FROM Item i LEFT JOIN i.locacoes l " +
            "WHERE (l IS NULL OR l.pago = true) " +
            "AND NOT EXISTS (SELECT 1 FROM Locacao l2 WHERE l2.item = i AND l2.pago = false)")
    List<Item> findItemsWithNoLocacoesOrPaidLocacoes();

    Optional<Item> findByNumSerie(int numSerie);
}
