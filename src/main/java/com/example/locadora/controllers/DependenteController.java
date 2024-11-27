package com.example.locadora.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.locadora.domain.Dependente;
import com.example.locadora.domain.Socio;
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
    public ResponseEntity<Dependente> salvar(@RequestBody Dependente dependente) throws Exception {
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

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta o dependente dado o ID.")
    public void deletar(@PathVariable Long id) throws Exception{
        dependenteService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os dependentes.")
    public ResponseEntity<List<Dependente>> listarTodos() {
        List<Dependente> dependentes = dependenteService.listar();
        return ResponseEntity.ok(dependentes);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca o depende por ID.")
    public ResponseEntity<Dependente> buscarPorId(@PathVariable Long id) {
        Dependente dependente = dependenteService.buscarPorId(id);
        return ResponseEntity.ok(dependente);
    }

    @PutMapping("/desativar/{id}")
    @Operation(description = "Dado o id, o dependente é desativado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o dependente seja desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Dependente> desativar(@PathVariable Long id) {
        Dependente dependenteDesativado = dependenteService.desativarDependente(id);
        return ResponseEntity.ok(dependenteDesativado);
    }

    @PutMapping("/reativar/{id}")
    @Operation(description = "Dado o id, o dependente é desativado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o dependente seja desativado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Dependente> reativar(@PathVariable Long id) {
        Dependente dependenteReativado = dependenteService.reativarDependente(id);
        return ResponseEntity.ok(dependenteReativado);
    }
}
