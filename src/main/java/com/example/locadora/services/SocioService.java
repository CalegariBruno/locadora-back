package com.example.locadora.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.locadora.domain.Dependente;
import com.example.locadora.domain.Socio;
import com.example.locadora.repositories.SocioRepository;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;
    
    public Socio salvar(Socio socio){
        
        socio.setAtivo(true);
        socio.setNumeroInscricao(gerarNumeroInscricao()); 

        return socioRepository.save(socio);
    }

    public Socio editar(Long id, Socio socioAtualizado){

        Optional<Socio> socioExistente = socioRepository.findById(id);

        if (socioExistente.isPresent()) {
            Socio socio = socioExistente.get();
            socio.setNome(socioAtualizado.getNome());
            socio.setTelefone(socioAtualizado.getTelefone());
            socio.setCpf(socioAtualizado.getCpf());
            socio.setDataNascimento(socioAtualizado.getDataNascimento());
            socio.setEndereco(socioAtualizado.getEndereco());
            socio.setLocacoes(socioAtualizado.getLocacoes());
            return socioRepository.save(socio);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio não encontrado");
        }

    }

    public Dependente inserirDependente (Long socioId, Dependente dependente) {

        Optional<Socio> socioExistente = socioRepository.findById(socioId);

        Socio socio = socioExistente.get();
        if ( socio.getDependentes().size() < 3 ) {
            throw new RuntimeException("O sócio já possui o número máximo de dependentes (3)");            
        } else {
            dependente.setAtivo(true);
            dependente.setSocio(socio);
            socio.getDependentes().add(dependente);
            dependente.setNumeroInscricao(gerarNumeroInscricao());
            socioRepository.save(socio);
        }
        return dependente;
    }

    public void deletar(Long id) throws Exception{

        Optional<Socio> socioExistente = socioRepository.findById(id);

        if (socioExistente.isPresent()) {

            if (!socioExistente.get().getDependentes().isEmpty()) {
                socioExistente.get().getDependentes().clear();
            } 

            if(socioExistente.get().getLocacoes().isEmpty()){
                socioRepository.delete(socioExistente.get());
            }else{
                throw new Exception("Cliente está relacionado a uma ou mais locações e não pode ser excluído.");
            }

            socioRepository.delete(socioExistente.get());
        }
    }

    public List<Socio> listar() {
        return socioRepository.findAll();
    }

    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio não encontrado"));
    }

    public void desativarSocio (Long id) {
        Optional<Socio> socioExistente = socioRepository.findById(id);

        if (socioExistente.isPresent()) {
            socioExistente.get().setAtivo(false);
            for(Dependente d : socioExistente.get().getDependentes()){
                d.setAtivo(false);
            }
        }
    }

    public void reativarSocio (Long id) {
        Optional<Socio> socioExistente = socioRepository.findById(id);

        if (socioExistente.isPresent()) {
            socioExistente.get().setAtivo(true);
            for(Dependente d : socioExistente.get().getDependentes()){
                d.setAtivo(true);
            }
        }
    }

    public void desativarDependente (Dependente dependente) {
        dependente.setAtivo(false);
    }

    public void reativarDependente (Dependente dependente) {
        dependente.setAtivo(true);
    }

    public int gerarNumeroInscricao() {
        Random random = new Random();
        int numero;

        do {
            numero = 100000000 + random.nextInt(900000000); 
        } while (socioRepository.existsByNumeroInscricao(numero)); // Verifica unicidade no banco

        return numero;
    }    
    
}
