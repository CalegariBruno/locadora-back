package com.example.locadora.controllers;

import com.example.locadora.domain.Diretor;
import com.example.locadora.services.DiretorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/api/diretores")
public class DiretorController {

    @Autowired
    private DiretorService diretorService;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um diretor.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o diretor seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Diretor> salvar(@RequestBody Diretor diretor) {
        Diretor novoDiretor = diretorService.salvar(diretor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDiretor);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, o diretor é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o diretor seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Diretor> editar(@PathVariable Long id, @RequestBody Diretor diretor) {
        Diretor diretorEditado = diretorService.editar(id, diretor);
        return ResponseEntity.ok(diretorEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta o diretor dado o ID.")
    public void deletar(@PathVariable Long id) throws Exception{
        diretorService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os diretores.")
    public ResponseEntity<List<Diretor>> listarTodos() {
        List<Diretor> diretores = diretorService.listar();
        return ResponseEntity.ok(diretores);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca o diretor por ID.")
    public ResponseEntity<Diretor> buscarPorId(@PathVariable Long id) {
        Diretor diretor = diretorService.buscarPorId(id);
        return ResponseEntity.ok(diretor);
    }
}
