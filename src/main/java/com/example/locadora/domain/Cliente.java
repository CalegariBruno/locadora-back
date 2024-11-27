package com.example.locadora.domain;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.example.locadora.domain.enums.sexoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", 
    allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private int numeroInscricao;

    private String nome;
    private LocalDate dataNascimento;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private sexoEnum sexo;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private Set<Locacao> locacoes; 

}
