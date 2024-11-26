package com.example.locadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long ano;

    private String sinopse;

    private String categoria;

    @ManyToOne
    @JoinColumn(name = "id_classe", nullable = false)
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "id_diretor", nullable = false)
    private Diretor diretor;

    @JsonIgnore
    @OneToMany(mappedBy = "titulo")
    private Set<Item> itens;

    @ManyToMany
    @JoinTable(
            name = "titulo_ator",
            joinColumns = @JoinColumn(name = "id_titulo"),
            inverseJoinColumns = @JoinColumn(name = "id_ator")
    )
    private Set<Ator> atores;

}
