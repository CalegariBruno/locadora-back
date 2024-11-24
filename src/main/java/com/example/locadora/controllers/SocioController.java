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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.locadora.domain.Socio;
import com.example.locadora.services.SocioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@Controller
@RequestMapping("/api/socios")
public class SocioController {
    
    @Autowired
    private SocioService socioService;

    @PostMapping("/criar")
    @Operation(description = "Dado o nome, cadastra um socio.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o socio seja inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Socio> salvar(@RequestBody Socio socio) {
        Socio novosocio = socioService.salvar(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novosocio);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Dado o id, o socio é editado.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o socio seja editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a alguma coisa que foi entendida como um erro do cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Socio> editar(@PathVariable Long id, @RequestBody Socio socio) {
        Socio socioEditado = socioService.editar(id, socio);
        return ResponseEntity.ok(socioEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta o socio dado o ID.")
    public void deletar(@PathVariable Long id) throws Exception{
        socioService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todos os socios.")
    public ResponseEntity<List<Socio>> listarTodos() {
        List<Socio> socios = socioService.listar();
        return ResponseEntity.ok(socios);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca o socio por ID.")
    public ResponseEntity<Socio> buscarPorId(@PathVariable Long id) {
        Socio socio = socioService.buscarPorId(id);
        return ResponseEntity.ok(socio);
    }

}
