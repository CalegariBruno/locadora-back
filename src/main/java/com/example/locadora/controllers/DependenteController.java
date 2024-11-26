package com.example.locadora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.locadora.domain.Dependente;
import com.example.locadora.services.DependenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
@RequestMapping("/api/dependentes")
public class DependenteController {

    @Autowired
    private DependenteService dependenteService;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um Dependente.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Dependente seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Dependente> salvar(@RequestBody Dependente dependente) {
        Dependente novoDependente = dependenteService.salvar(dependente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDependente);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, o Dependente é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Dependente seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Dependente> editar(@PathVariable Long id, @RequestBody Dependente dependente) {
        Dependente dependenteEditado = dependenteService.editar(id, dependente);
        return ResponseEntity.ok(dependenteEditado);
    }
}
