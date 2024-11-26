package com.example.locadora.controllers;

import com.example.locadora.domain.Item;
import com.example.locadora.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/criar")
    @Operation(description = "Cria um novo item, fornecendo os detalhes necessários.", responses = {
            @ApiResponse(responseCode = "201", description = "Caso o item seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Item> salvar(@RequestBody Item item) {
        Item novoItem = itemService.salvar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Edita um item existente dado o ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o item seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "404", description = "Caso o item não seja encontrado."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Item> editar(@PathVariable Long id, @RequestBody Item itemAtualizado) {
        Item itemEditado = itemService.editar(id, itemAtualizado);
        return ResponseEntity.ok(itemEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta um item dado o ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso o item não seja encontrado."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public void deletar(@PathVariable Long id) throws Exception {
        itemService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os itens cadastrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso.")
    })
    public ResponseEntity<List<Item>> listarTodos() {
        List<Item> itens = itemService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca um item pelo ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Item retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso o item não seja encontrado.")
    })
    public ResponseEntity<Item> buscarPorId(@PathVariable Long id) {
        Item item = itemService.buscarPorId(id);
        return ResponseEntity.ok(item);
    }
}
