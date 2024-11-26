package com.example.locadora.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.locadora.domain.Dependente;
import com.example.locadora.repositories.DependenteRepository;

@Service
public class DependenteService {

    @Autowired
    private DependenteRepository dependenteRepository;

    public Dependente salvar(Dependente dependente) {
        dependente.setNumeroInscricao(gerarNumeroInscricao());
        return dependenteRepository.save(dependente);
    }

    public Dependente editar(Long id, Dependente dependenteAtualizado){

        Optional<Dependente> dependenteExistente = dependenteRepository.findById(id);

        if (dependenteExistente.isPresent()) {
            Dependente dependente = dependenteExistente.get();
            dependente.setNome(dependenteAtualizado.getNome());
            dependente.setDataNascimento(dependenteAtualizado.getDataNascimento());
            dependente.setLocacoes(dependenteAtualizado.getLocacoes());
            dependente.setNumeroInscricao(dependenteAtualizado.getNumeroInscricao());
            dependente.setSexo(dependenteAtualizado.getSexo());
            dependente.setSocio(dependenteAtualizado.getSocio());            
            return dependenteRepository.save(dependente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dependente não encontrado");
        }

    }

    public void deletar(Long id) throws Exception{

        Optional<Dependente> dependente = dependenteRepository.findById(id);

        if (dependente.isPresent()) {

            if(dependente.get().getLocacoes().isEmpty()){
                dependenteRepository.delete(dependente.get());
            }else{
                throw new Exception("Cliente está relacionado a uma ou mais locações e não pode ser excluído.");
            }

            dependenteRepository.delete(dependente.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
        }
    }

    public List<Dependente> listarTodos() {
        return dependenteRepository.findAll();
    }

    public Dependente buscarPorId(Long id) {
        return dependenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    public int gerarNumeroInscricao() {
        Random random = new Random();
        int numero;

        do {
            numero = 100000000 + random.nextInt(900000000); 
        } while (dependenteRepository.existsByNumeroInscricao(numero)); // Verifica unicidade no banco

        return numero;
    }  
}
