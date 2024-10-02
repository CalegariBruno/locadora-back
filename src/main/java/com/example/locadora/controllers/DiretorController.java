package com.example.locadora.controllers;

import com.example.locadora.domain.Diretor;
import com.example.locadora.services.DiretorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diretores")
public class DiretorController {

    @Autowired
    private DiretorService diretorService;

    @PostMapping("/criar")
    public ResponseEntity<Diretor> salvar(@RequestBody Diretor diretor) {
        Diretor novoDiretor = diretorService.salvar(diretor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDiretor);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Diretor> editar(@PathVariable Long id, @RequestBody Diretor diretor) {
        Diretor diretorEditado = diretorService.editar(id, diretor);
        return ResponseEntity.ok(diretorEditado);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        diretorService.deletar(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Diretor>> listarTodos() {
        List<Diretor> diretores = diretorService.listar();
        return ResponseEntity.ok(diretores);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Diretor> buscarPorId(@PathVariable Long id) {
        Diretor diretor = diretorService.buscarPorId(id);
        return ResponseEntity.ok(diretor);
    }
}
