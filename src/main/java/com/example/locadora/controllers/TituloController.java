package com.example.locadora.controllers;

import com.example.locadora.domain.Titulo;
import com.example.locadora.services.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/titulos")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @PostMapping("/criar")
    @Operation(description = "Cria um novo título, fornecendo os detalhes necessários.", responses = {
            @ApiResponse(responseCode = "201", description = "Caso o título seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Titulo> salvar(@RequestBody Titulo titulo) {
        Titulo novoTitulo = tituloService.salvar(titulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTitulo);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Edita um título existente dado o ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o título seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "404", description = "Caso o título não seja encontrado."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Titulo> editar(@PathVariable Long id, @RequestBody Titulo tituloAtualizado) {
        Titulo tituloEditado = tituloService.editar(id, tituloAtualizado);
        return ResponseEntity.ok(tituloEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta um título dado o ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Título deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso o título não seja encontrado."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public void deletar(@PathVariable Long id) {
        tituloService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os títulos cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de títulos retornada com sucesso.")
    })
    public ResponseEntity<List<Titulo>> listarTodos() {
        List<Titulo> titulos = tituloService.listarTodos();
        return ResponseEntity.ok(titulos);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca um título pelo ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Título retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso o título não seja encontrado.")
    })
    public ResponseEntity<Titulo> buscarPorId(@PathVariable Long id) {
        Titulo titulo = tituloService.buscarPorId(id);
        return ResponseEntity.ok(titulo);
    }
}
