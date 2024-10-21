package com.example.locadora.domain;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Titulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long ano;

    private String sinopse;

    private String categoria;

    @ManyToOne
    @JoinColumn(name="classe_id")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name="diretor_id")
    private Diretor diretor;

    @ManyToMany
    @JoinTable(
        name="titulo_ator",
        joinColumns = @JoinColumn(name="titulo_id"),
        inverseJoinColumns = @JoinColumn(name="ator_id")
    )
    private Set<Ator> atores;

}
