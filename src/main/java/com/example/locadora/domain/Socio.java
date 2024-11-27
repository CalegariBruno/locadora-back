package com.example.locadora.domain;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Socio extends Cliente{

    private String endereco;

    @Column(nullable = false, unique = true)
    private String cpf;
    
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "socio")
    private Set<Dependente> dependentes;

}

