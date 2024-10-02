package com.example.locadora.services;

import com.example.locadora.domain.Ator;
import com.example.locadora.repositories.AtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AtorService {

    @Autowired
    private AtorRepository atorRepository;

    public Ator salvar(Ator ator) {
        return atorRepository.save(ator);
    }

    public Ator editar(Long id, Ator atorAtualizado) {

        Optional<Ator> atorExistente = atorRepository.findById(id);

        if (atorExistente.isPresent()) {
            Ator ator = atorExistente.get();
            ator.setNome(atorAtualizado.getNome());
            return atorRepository.save(ator);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ator não encontrado!");
        }

    }

    public void deletar(Long id) {

        Optional<Ator> ator = atorRepository.findById(id);

        if (ator.isPresent()) {
            atorRepository.delete(ator.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ator não encontrado!");
        }

    }

    public List<Ator> listarTodos() {
        return atorRepository.findAll();
    }

    public Ator buscarPorId(Long id) {
        return atorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ator não encontrado!"));
    }
}
