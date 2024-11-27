package com.example.locadora.controllers;


import com.example.locadora.domain.Ator;
import com.example.locadora.domain.Cliente;
import com.example.locadora.domain.Dependente;
import com.example.locadora.domain.Socio;
import com.example.locadora.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    @Operation(description = "Lista todos os clientes.")
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/socio/criar")
    @Operation(description = "Dado o nome, cadastra um socio.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o socio seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Socio> salvarSocio(@RequestBody Socio socio) {
        Socio novoSocio = clienteService.criarSocio(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoSocio);
    }

    @PostMapping("/dependente/criar")
    @Operation(description = "Dado o nome, cadastra um dependente.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o dependente seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Dependente> salvarSocio(@RequestBody Dependente dependente) {
        Dependente novoDependente = clienteService.criarDependente(dependente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDependente);
    }



}
