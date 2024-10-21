package com.example.locadora.domain;

import java.time.LocalDate;

import com.example.locadora.domain.enums.tipoItemEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numSerie;

    private LocalDate dtAquisicao;

    @Enumerated(EnumType.STRING)
    private tipoItemEnum tipoItem;
}
