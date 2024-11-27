package com.example.locadora.controllers;

import com.example.locadora.domain.Locacao;
import com.example.locadora.dtos.LocacaoDTO;
import com.example.locadora.services.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping("/efetuarLocacao")
    @Operation(description = "Cria uma nova locação, fornecendo os detalhes necessários.", responses = {
            @ApiResponse(responseCode = "201", description = "Caso a locação seja inserida com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Locacao> criarLocacao(@RequestBody LocacaoDTO locacao) {
        Locacao novaLocacao = locacaoService.efetuarLocacao(locacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLocacao);
    }

    @PostMapping("/efetuarDevolucao")
    @Operation(description = "Cria uma nova devolução, fornecendo os detalhes necessários.", responses = {
            @ApiResponse(responseCode = "201", description = "Caso a locação seja inserida com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Locacao> criarDevolucao(@RequestBody int numSerieItem, @RequestBody Double multa) {
        Locacao novaLocacao = locacaoService.efetuarDevolucao(numSerieItem,multa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLocacao);
    }

    @PutMapping("/editar/{id}")
    @Operation(description = "Edita uma locação existente dado o ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a locação seja editada com sucesso."),
            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
            @ApiResponse(responseCode = "404", description = "Caso a locação não seja encontrada."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public ResponseEntity<Locacao> editar(@PathVariable Long id, @RequestBody LocacaoDTO locacaoAtualizada) {
        Locacao locacaoEditada = locacaoService.editarEfetuarLocacao(id, locacaoAtualizada);
        return ResponseEntity.ok(locacaoEditada);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Deleta uma locação dado o ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Locação deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso a locação não seja encontrada."),
            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
    })
    public void deletar(@PathVariable Long id) throws Exception{
        locacaoService.deletar(id);
    }

    @GetMapping("/listar")
    @Operation(description = "Lista todas as locações cadastradas.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de locações retornada com sucesso.")
    })
    public ResponseEntity<List<Locacao>> listarTodos() {
        List<Locacao> locacoes = locacaoService.listarTodos();
        return ResponseEntity.ok(locacoes);
    }

    @GetMapping("/buscar/{id}")
    @Operation(description = "Busca uma locação pelo ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Locação retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso a locação não seja encontrada.")
    })
    public ResponseEntity<Locacao> buscarPorId(@PathVariable Long id) {
        Locacao locacao = locacaoService.buscarPorId(id);
        return ResponseEntity.ok(locacao);
    }


    //    @PutMapping("/pagamento/{id}")
//    @Operation(description = "Edita uma locação existente dado o ID.", responses = {
//            @ApiResponse(responseCode = "200", description = "Caso a locação seja editada com sucesso."),
//            @ApiResponse(responseCode = "400", description = "O servidor não pode processar a requisição devido a um erro no cliente."),
//            @ApiResponse(responseCode = "404", description = "Caso a locação não seja encontrada."),
//            @ApiResponse(responseCode = "500", description = "Caso não tenha sido possível realizar a operação.")
//    })
//    public ResponseEntity<Locacao> pagamento(@PathVariable Long id) {
//        Locacao locacaoEditada = locacaoService.efetuarPagamento(id);
//        return ResponseEntity.ok(locacaoEditada);
//    }

}
