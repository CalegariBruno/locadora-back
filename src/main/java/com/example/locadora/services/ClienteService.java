package com.example.locadora.services;

import com.example.locadora.domain.Ator;
import com.example.locadora.domain.Cliente;
import com.example.locadora.domain.Dependente;
import com.example.locadora.domain.Socio;
import com.example.locadora.repositories.ClienteRepository;
import com.example.locadora.repositories.DependenteRepository;
import com.example.locadora.repositories.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DependenteRepository dependenteRepository;    

    public Socio criarSocio(Socio socio){

        socio.setAtivo(true);
        socio.setNumeroInscricao(gerarNumeroInscricao());

        return socioRepository.save(socio);
    }

    public Dependente criarDependente(Dependente dependente){

        dependente.setAtivo(true);
        dependente.setNumeroInscricao(gerarNumeroInscricao());

        return dependenteRepository.save(dependente);
    }

    public Socio editarSocio(Long id, Socio socioAtualizado){

        Optional<Socio> socioExistente = socioRepository.findById(id);

        if(socioExistente.isPresent()){
            Socio socio = socioExistente.get();
            socio.setNumeroInscricao(socioExistente.get().getNumeroInscricao());
            socio.setCpf(socioAtualizado.getCpf());
            socio.setNome(socioAtualizado.getNome());
            socio.setDataNascimento(socioAtualizado.getDataNascimento());
            socio.setAtivo(socioAtualizado.isAtivo());
            socio.setSexo(socioAtualizado.getSexo());
            socio.setLocacoes(socioExistente.get().getLocacoes());
            socio.setTelefone(socioAtualizado.getTelefone());
            socio.setDependentes(socioExistente.get().getDependentes());
            return socioRepository.save(socio);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sócio não encontrado!");
        }

    }

    public Dependente editarDependente(Long id, Dependente dependenteAtualizado){

        Optional<Dependente> dependenteExistente = dependenteRepository.findById(id);

        if(dependenteExistente.isPresent()){

            Dependente dependente = dependenteExistente.get();
            dependente.setNumeroInscricao(dependenteExistente.get().getNumeroInscricao());
            dependente.setNome(dependenteAtualizado.getNome());
            dependente.setDataNascimento(dependenteAtualizado.getDataNascimento());
            dependente.setAtivo(dependenteAtualizado.isAtivo());
            dependente.setSexo(dependenteAtualizado.getSexo());
            dependente.setLocacoes(dependenteExistente.get().getLocacoes());
            dependente.setSocio(dependenteAtualizado.getSocio());

            return dependenteRepository.save(dependente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dependente não encontrado!");
        }

    }

    public void deletarSocio(Long id) throws Exception{

    }

    public void deletarDependente(Long id) throws Exception{

    }

    public List<Cliente> listarTodosClientes(){
        return clienteRepository.findAll();
    }

    public List<Socio> listarTodosSocios(){
        return socioRepository.findAll();
    }

    public List<Dependente> listarTodosDependentes(){
        return dependenteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    private int gerarNumeroInscricao() {

        int numero;
        Random random = new Random();

        do {
            numero = 100000000 + random.nextInt(900000000);
        } while (clienteRepository.existsByNumeroInscricao(numero)); // Verifica unicidade no banco

        return numero;
    }

}
