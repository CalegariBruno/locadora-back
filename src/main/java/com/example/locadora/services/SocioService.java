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
import com.example.locadora.repositories.DependenteRepository;
import com.example.locadora.repositories.SocioRepository;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DependenteRepository dependenteRepository;
    
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

    public List<Socio> listarSociosLiberados () {
        List<Socio> sociosLiberados = socioRepository.findAll();

        for(Socio s : sociosLiberados) {
            if (s.isAtivo()) {
                if(!temMenosDeTresAtivos(s)) {
                    sociosLiberados.remove(s);
                }
            }
            
        }
        return sociosLiberados;
    }

    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio não encontrado"));
    }

    public Socio desativarSocio (Long id) {
        Optional<Socio> socioExistente = socioRepository.findById(id);

        if (socioExistente.isPresent()) {
            socioExistente.get().setAtivo(false);
            for(Dependente d : socioExistente.get().getDependentes()){
                d.setAtivo(false);
                dependenteRepository.save(d);
            }
        }
        
        return socioRepository.save(socioExistente.get());
    }

    public Socio reativarSocio (Long id) {
        Optional<Socio> socioExistente = socioRepository.findById(id);

        if (socioExistente.isPresent()) {
            socioExistente.get().setAtivo(true);
            int i = 0;
            for(Dependente d : socioExistente.get().getDependentes()){
                if (i == 3) {
                    break;
                }
                d.setAtivo(true);
                dependenteRepository.save(d);
                i++;
            }
        }
        return socioRepository.save(socioExistente.get());
    }

    public int gerarNumeroInscricao() {
        Random random = new Random();
        int numero;

        do {
            numero = 100000000 + random.nextInt(900000000); 
        } while (socioRepository.existsByNumeroInscricao(numero)); // Verifica unicidade no banco

        return numero;
    }  
    
    private boolean temMenosDeTresAtivos (Socio socio) {

        if (!socio.getDependentes().isEmpty()) {
            
            int ativos = 0;
            for(Dependente dependente : socio.getDependentes()) {
                if(dependente.isAtivo()) {
                    ativos++;
                    if (ativos >= 3) { // Interrompe o loop se encontrar 3 ativos
                        return false;
                    }
                }
            }    
        } 

        return true;
    }
}
