package com.example.locadora.controllers;

import com.example.locadora.domain.Classe;
import com.example.locadora.services.ClasseService;
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
    public ResponseEntity<Classe> salvar(@RequestBody Classe classe) {
        Classe novaClasse = classeService.salvar(classe);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaClasse);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Classe> editar(@PathVariable Long id, @RequestBody Classe classeAtualizada) {
        Classe classeEditada = classeService.editar(id, classeAtualizada);
        return ResponseEntity.ok(classeEditada);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        classeService.deletar(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Classe>> listarTodos() {
        List<Classe> classes = classeService.listarTodos();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Classe> buscarPorId(@PathVariable Long id) {
        Classe classe = classeService.buscarPorId(id);
        return ResponseEntity.ok(classe);
    }
}
