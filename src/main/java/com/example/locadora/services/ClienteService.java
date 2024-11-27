package com.example.locadora.services;

import com.example.locadora.domain.Cliente;
import com.example.locadora.domain.Dependente;
import com.example.locadora.domain.Socio;
import com.example.locadora.repositories.ClienteRepository;
import com.example.locadora.repositories.DependenteRepository;
import com.example.locadora.repositories.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DependenteRepository dependenteRepository;

    public List<Cliente> listarTodosClientes(){
        return clienteRepository.findAll();
    }

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

    public int gerarNumeroInscricao() {

        int numero;
        Random random = new Random();

        do {
            numero = 100000000 + random.nextInt(900000000);
        } while (clienteRepository.existsByNumeroInscricao(numero)); // Verifica unicidade no banco

        return numero;
    }

}
