package com.example.locadora.controllers;

import com.example.locadora.domain.Classe;
import com.example.locadora.services.ClasseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra uma classe.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a classe seja inserida com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Classe> salvar(@RequestBody Classe classe) {
        Classe novaClasse = classeService.salvar(classe);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaClasse);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, a classe é editada.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a classe seja editada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Classe> editar(@PathVariable Long id, @RequestBody Classe classeAtualizada) {
        Classe classeEditada = classeService.editar(id, classeAtualizada);
        return ResponseEntity.ok(classeEditada);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta a classe dado o ID.")
    public void deletar(@PathVariable Long id) throws Exception{
        classeService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todas as classes cadastradas.")
    public ResponseEntity<List<Classe>> listarTodos() {
        List<Classe> classes = classeService.listarTodos();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca classe por ID.")
    public ResponseEntity<Classe> buscarPorId(@PathVariable Long id) {
        Classe classe = classeService.buscarPorId(id);
        return ResponseEntity.ok(classe);
    }
}
