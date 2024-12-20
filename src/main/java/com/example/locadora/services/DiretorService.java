package com.example.locadora.services;

import com.example.locadora.domain.Diretor;
import com.example.locadora.repositories.DiretorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class DiretorService {

    @Autowired
    private DiretorRepository diretorRepository;

    public Diretor salvar(Diretor diretor) {
        return diretorRepository.save(diretor);
    }

    public Diretor editar(Long id, Diretor diretorAtualizado) {

        Optional<Diretor> diretorExistente = diretorRepository.findById(id);

        if (diretorExistente.isPresent()) {
            Diretor diretor = diretorExistente.get();
            diretor.setNome(diretorAtualizado.getNome());
            return diretorRepository.save(diretor);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diretor não encontrado");
        }
    }

    public void deletar(Long id) throws Exception{

        Optional<Diretor> diretorExistente = diretorRepository.findById(id);

        if (diretorExistente.isPresent()) {

            if(diretorExistente.get().getTitulos().isEmpty()){
                diretorRepository.delete(diretorExistente.get());
            }else{
                throw new Exception("Diretor está relacionado a um ou mais títulos e não pode ser excluído.");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diretor não encontrado");
        }
    }

    public List<Diretor> listar() {
        return diretorRepository.findAll();
    }

    public Diretor buscarPorId(Long id) {
        return diretorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diretor não encontrado"));
    }
}
