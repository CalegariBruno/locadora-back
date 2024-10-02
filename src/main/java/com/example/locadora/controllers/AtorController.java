package com.example.locadora.controllers;

import com.example.locadora.domain.Ator;
import com.example.locadora.services.AtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atores")
public class AtorController {

    @Autowired
    private AtorService atorService;

    @PostMapping("/criar")
    public ResponseEntity<Ator> salvar(@RequestBody Ator ator) {
        Ator novoAtor = atorService.salvar(ator);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtor);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Ator> editar(@PathVariable Long id, @RequestBody Ator atorAtualizado) {
        Ator atorEditado = atorService.editar(id, atorAtualizado);
        return ResponseEntity.ok(atorEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        atorService.deletar(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Ator>> listarTodos() {
        List<Ator> atores = atorService.listarTodos();
        return ResponseEntity.ok(atores);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Ator> buscarPorId(@PathVariable Long id) {
        Ator ator = atorService.buscarPorId(id);
        return ResponseEntity.ok(ator);
    }
}
