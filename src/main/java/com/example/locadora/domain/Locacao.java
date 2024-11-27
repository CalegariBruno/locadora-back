package com.example.locadora.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Locacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dtLocacao;

    private LocalDate dtDevolucaoPrevista;

    private LocalDate dtDevolucaoEfetiva;

    private Double valorCobrado;

    private Double multaCobrada = 0.0;

    private boolean pago = false;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

}
