package com.example.locadora.services;

import com.example.locadora.domain.Titulo;
import com.example.locadora.repositories.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;

    public Titulo salvar(Titulo titulo) {
        verificarAtoresNaoNulos(titulo);
        return tituloRepository.save(titulo);
    }

    public Titulo editar(Long id, Titulo tituloAtualizado) {

        Optional<Titulo> tituloExistente = tituloRepository.findById(id);

        if (tituloExistente.isPresent()) {
            Titulo titulo = tituloExistente.get();
            titulo.setNome(tituloAtualizado.getNome());
            titulo.setAno(tituloAtualizado.getAno());
            titulo.setSinopse(tituloAtualizado.getSinopse());
            titulo.setCategoria(tituloAtualizado.getCategoria());
            titulo.setClasse(tituloAtualizado.getClasse());
            titulo.setDiretor(tituloAtualizado.getDiretor());
            titulo.setAtores(tituloAtualizado.getAtores());

            verificarAtoresNaoNulos(titulo);
            return tituloRepository.save(titulo);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado!");
        }
    }

    public void deletar(Long id) throws Exception{

        Optional<Titulo> titulo = tituloRepository.findById(id);

        if (titulo.isPresent()) {

            if(titulo.get().getItens().isEmpty()){
                tituloRepository.delete(titulo.get());
            } else{
                throw new Exception("Título está relacionado a um ou mais itens e não pode ser excluído.");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado!");
        }
    }

    public List<Titulo> listarTodos() {
        return tituloRepository.findAll();
    }

    public List<Titulo> listarPorNome(String nome){
        return tituloRepository.findByNome(nome);
    }

    public List<Titulo> listarPorCategoria(String categoria){
        return tituloRepository.findByCategoria(categoria);
    }

    public List<Titulo> listarPorAtor(String ator){
        return tituloRepository.findByAtores_Nome(ator);
    }

    public Titulo buscarPorId(Long id) {
        return tituloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado!"));
    }

    private void verificarAtoresNaoNulos(Titulo titulo) {
        if (titulo.getAtores() == null || titulo.getAtores().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um título deve ter pelo menos um ator.");
        }
    }

}
