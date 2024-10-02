package com.example.locadora.services;

import com.example.locadora.domain.Classe;
import com.example.locadora.repositories.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public Classe salvar(Classe classe) {
        return classeRepository.save(classe);
    }

    public Classe editar(Long id, Classe classeAtualizada) {

        Optional<Classe> classeExistente = classeRepository.findById(id);

        if (classeExistente.isPresent()) {
            Classe classe = classeExistente.get();
            classe.setNome(classeAtualizada.getNome());
            classe.setValor(classeAtualizada.getValor());
            classe.setPrazoDevolucao(classeAtualizada.getPrazoDevolucao());
            return classeRepository.save(classe);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Classe não encontrada");
        }
    }

    public void deletar(Long id) {

        Optional<Classe> classeExistente = classeRepository.findById(id);

        if (classeExistente.isPresent()) {
            classeRepository.delete(classeExistente.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Classe não encontrada");
        }
    }

    public List<Classe> listarTodos() {
        return classeRepository.findAll();
    }

    public Classe buscarPorId(Long id) {
        return classeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classe não encontrada"));
    }
}
