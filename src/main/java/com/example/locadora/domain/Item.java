package com.example.locadora.domain;

import java.time.LocalDate;

import com.example.locadora.domain.enums.tipoItemEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numSerie;

    private LocalDate dtAquisicao;

    @Enumerated(EnumType.STRING)
    private tipoItemEnum tipoItem;

    @ManyToOne
    @JoinColumn(name = "id_titulo", nullable = false)
    private Titulo titulo;
}
