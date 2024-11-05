package com.example.locadora.controllers;

import com.example.locadora.domain.Ator;
import com.example.locadora.services.AtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/atores")
public class AtorController {

    @Autowired
    private AtorService atorService;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um ator.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o ator seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Ator> salvar(@RequestBody Ator ator) {
        Ator novoAtor = atorService.salvar(ator);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtor);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, o ator é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o ator seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Ator> editar(@PathVariable Long id, @RequestBody Ator atorAtualizado) {
        Ator atorEditado = atorService.editar(id, atorAtualizado);
        return ResponseEntity.ok(atorEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta o ator dado o ID.")
    public void deletar(@PathVariable Long id) throws Exception{
        atorService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os atores.")
    public ResponseEntity<List<Ator>> listarTodos() {
        List<Ator> atores = atorService.listarTodos();
        return ResponseEntity.ok(atores);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca ator por ID.")
    public ResponseEntity<Ator> buscarPorId(@PathVariable Long id) {
        Ator ator = atorService.buscarPorId(id);
        return ResponseEntity.ok(ator);
    }
}
