package com.example.locadora.services;

import com.example.locadora.domain.*;
import com.example.locadora.repositories.ClienteRepository;
import com.example.locadora.repositories.DependenteRepository;
import com.example.locadora.repositories.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired LocacaoService locacaoService;

    public Socio criarSocio(Socio socio){

        socio.setAtivo(true);
        socio.setNumeroInscricao(gerarNumeroInscricao());

        return socioRepository.save(socio);
    }

    public Dependente criarDependente(Dependente dependente) throws Exception {

        // Verificar se o sócio já possui 3 dependentes
        Set<Dependente> dependentes = dependente.getSocio().getDependentes();

        // Se dependentes for null, inicialize-o como uma coleção vazia
        if (dependentes == null) {
            dependentes = new HashSet<>();
        }

        if (dependentes.size() >= 3) {
            // Verificar se o sócio já possui 3 dependentes ativos
            int dependentesAtivos = 0;

            for (Dependente dep : dependentes) {
                if (dep.isAtivo()) {
                    dependentesAtivos++;
                }
            }

            if (dependentesAtivos >= 3) {
                throw new Exception("Sócio já possui 3 dependentes ativos!");
            }
        }

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

    @Transactional
    public void deletarSocio(Long id) throws Exception{

        Optional<Socio> socio = socioRepository.findById(id);

        // Verifica se o sócio existe
        if(socio.isPresent()){

            Socio socioAux = socio.get();

            // verificar se cliente tem locações ou seja, se alguma locação de sua lista não está paga
            // se todas as locações estiverem pagas, deletar todas as locações
            if(!socioAux.getLocacoes().isEmpty()){//se a lista de locações não estiver vazia
                for(Locacao locacao : socioAux.getLocacoes()){//percorre a lista de locações
                    if(!locacao.isPago()){//se a locação não estiver paga
                        throw new Exception("Sócio possui locações pendentes!");
                    }
                }

                // deleta as locações
                for(Locacao locacao : socioAux.getLocacoes()){//percorre a lista de locações
                    locacaoService.deletar(locacao.getId());//deleta a locação
                }

            }

            // verifica se o sócio tem dependentes
            if(!socioAux.getDependentes().isEmpty()){//se a lista de dependentes não estiver vazia
                // deleta os dependentes chamando o método deletar Dependente
                for(Dependente dependente : socioAux.getDependentes()) {//percorre a lista de dependentes
                    deletarDependente(dependente.getId());//deleta o dependente
                }
            }

            socioRepository.deleteById(id);
            clienteRepository.deleteById(id);
        } else {
            throw new Exception("Sócio não encontrado!");
        }

    }

    @Transactional
    public void deletarDependente(Long id) throws Exception{

        Optional<Dependente> dependente = dependenteRepository.findById(id);

        // Verifica se o dependente existe
        if(dependente.isPresent()){

            Dependente dependenteAux = dependente.get();

            // verificar se dependente tem locações ou seja, se alguma locação de sua lista não está paga
            // se todas as locações estiverem pagas, deletar todas as locações
            if(!dependenteAux.getLocacoes().isEmpty()){//se a lista de locações não estiver vazia
                for(Locacao locacao : dependenteAux.getLocacoes()){//percorre a lista de locações
                    if(!locacao.isPago()){//se a locação não estiver paga
                        throw new Exception("Dependente possui locações pendentes!");
                    }
                }

                // deleta as locações
                for(Locacao locacao : dependenteAux.getLocacoes()){//percorre a lista de locações
                    locacaoService.deletar(locacao.getId());//deleta a locação
                }
            }

            dependenteRepository.deleteById(id);
            clienteRepository.deleteById(id);
        } else {
            throw new Exception("Dependente não encontrado!");
        }

    }

    public List<Cliente> listarTodosClientes(){
        return clienteRepository.findAll();
    }

    public List<Cliente> listarClientesAtivos(){
        return clienteRepository.findByAtivoTrue();
    }

    public List<Socio> listarTodosSocios(){
        return socioRepository.findAll();
    }

    public List<Socio> listarSociosDisponiveis(){
        return socioRepository.listarSociosComMenosDe3DependentesAtivos();
    }

    public List<Dependente> listarTodosDependentes(){
        return dependenteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    public void alterarStatusSocio(Long id, boolean status) {

        // Busca o sócio pelo ID e lança uma exceção se não encontrado
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sócio não existe"));

        if (status) {
            // Ativar os três primeiros dependentes inativos
            int dependentesInativos = 0;
            for (Dependente dependente : socio.getDependentes()) {
                if (dependentesInativos < 3) {
                    dependente.setAtivo(true);
                    dependenteRepository.save(dependente);
                    dependentesInativos++;
                }
            }
        } else {
            // Desativar todos os dependentes
            for (Dependente dependente : socio.getDependentes()) {
                dependente.setAtivo(false);
                dependenteRepository.save(dependente);
            }
        }

        socio.setAtivo(status);
        socioRepository.save(socio);
    }

    public void alterarStatusDependente(Long id, boolean status) throws Exception{

        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dependente não existe"));

        // verificar se o socio do dependente está inativo
        if(!dependente.getSocio().isAtivo()){
            throw new Exception("Sócio do dependente está inativo!");
        }

        dependente.setAtivo(status);
        dependenteRepository.save(dependente);
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
