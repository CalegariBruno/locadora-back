package com.example.locadora.dtos;

import com.example.locadora.domain.Cliente;
import com.example.locadora.domain.Item;

import java.time.LocalDate;

public record LocacaoDTO(Cliente cliente, Item item, Double valorAlterado, LocalDate dtDevolucaoAlterado) {
}
