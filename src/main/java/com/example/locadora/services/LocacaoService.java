package com.example.locadora.services;

import com.example.locadora.domain.Classe;
import com.example.locadora.domain.Cliente;
import com.example.locadora.domain.Item;
import com.example.locadora.domain.Locacao;
import com.example.locadora.dtos.LocacaoDTO;
import com.example.locadora.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Locacao efetuarLocacao(LocacaoDTO locacao) throws Exception {

        Item item = itemRepository.findById(locacao.item().getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!"));

        Cliente cliente = clienteRepository.findById(locacao.cliente().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));

        // Verifica se o cliente está em débito
        if (clientePossuiDebito(cliente)) {
            throw new Exception("Cliente está em débito e não pode realizar a locação.");
        }

        // Verifica se o item já está locado
        if (itemJaLocado(item)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item já locado.");
        }

        // Obtém a classe do título associado ao item
        Classe classe = item.getTitulo().getClasse();

        // Calcula o valor da locação e a data de devolução prevista
        Double valorLocacao = classe.getValor();
        LocalDate dataLocacao = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataLocacao.plusDays(classe.getPrazoDevolucao());
        LocalDate dataDevolucaoEfetiva;

        // Permite alteração pelo funcionário
        if (locacao.valorAlterado() != null) {
            valorLocacao = locacao.valorAlterado();
        }

        if (locacao.dtDevolucaoAlterado() != null) {

            if(locacao.dtDevolucaoAlterado().isBefore(dataLocacao)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de devolução não pode ser anterior a data de locação.");
            }else{
                dataDevolucaoEfetiva = locacao.dtDevolucaoAlterado();
            }

        }else{
            dataDevolucaoEfetiva = dataDevolucaoPrevista;
        }

        // Cria uma nova locação
        Locacao novaLocacao = new Locacao();
        novaLocacao.setDtLocacao(dataLocacao);
        novaLocacao.setDtDevolucaoPrevista(dataDevolucaoPrevista);
        novaLocacao.setDtDevolucaoEfetiva(dataDevolucaoEfetiva);
        novaLocacao.setValorCobrado(valorLocacao);
        novaLocacao.setItem(item);
        novaLocacao.setCliente(cliente);

        // Salva a locação no repositório
        return locacaoRepository.save(novaLocacao);
    }

    @Transactional
    public Locacao editarEfetuarLocacao(Long id, LocacaoDTO locacaoAtualizada) throws Exception {

        Optional<Locacao> locacaoExistente = locacaoRepository.findById(id);

        if (locacaoExistente.isPresent()) {

            Locacao locacao = locacaoExistente.get();
            locacao.setDtLocacao(LocalDate.now());
            locacao.setValorCobrado(locacaoAtualizada.valorAlterado());
            locacao.setItem(locacaoAtualizada.item());
            locacao.setCliente(locacaoAtualizada.cliente());

            if(locacaoAtualizada.dtDevolucaoAlterado().isBefore(LocalDate.now())){
                throw new Exception( "Data de devolução não pode ser anterior a data de locação.");
            }else{
                locacao.setDtDevolucaoPrevista(locacaoAtualizada.dtDevolucaoAlterado());
                locacao.setDtDevolucaoEfetiva(locacaoAtualizada.dtDevolucaoAlterado());
            }

            return locacaoRepository.save(locacao);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada!");
        }
    }

    @Transactional
    public Locacao efetuarPagamento(Long id) {

        Optional<Locacao> locacaoExistente = locacaoRepository.findById(id);

        if (locacaoExistente.isPresent()) {

            Locacao locacao = locacaoExistente.get();
            locacao.setPago(true);

            return locacaoRepository.save(locacao);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada!");
        }
    }

    @Transactional
    public Locacao efetuarDevolucao(int numSerieItem, Double multa){

        Item item = itemRepository.findByNumSerie(numSerieItem)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!"));

        Locacao locacao = locacaoRepository.findByItemAndPagoFalse(item)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada!"));

        // verificar se a locacao ja foi paga
        if(locacao.isPago()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Devolução já foi feita.");
        }

        // verificar se a devolucao esta atrasada
        if(locacao.getDtDevolucaoEfetiva().isBefore(LocalDate.now())){
            locacao.setMultaCobrada(multa);
            locacao.setValorCobrado(locacao.getValorCobrado() + multa);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Devolução atrasada. Pague a multa.");
        }

        // registrar devolução
        locacao.setDtDevolucaoEfetiva(LocalDate.now());
        locacao.setPago(true);

        return locacaoRepository.save(locacao);
    }

    public void deletar(Long id) throws Exception {

        Optional<Locacao> locacao = locacaoRepository.findById(id);

        if (locacao.isPresent()) {

            Locacao locacaoDelete = locacao.get();

            if(locacaoDelete.isPago()){
                throw new Exception("Locação do cliente " + locacaoDelete.getCliente().getNome() + " já foi paga e não pode ser cancelada.");
            }else {
                locacaoRepository.delete(locacao.get());
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada!");
        }
    }

    public List<Locacao> listarTodos() {
        return locacaoRepository.findAll();
    }

    public Locacao buscarPorId(Long id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada!"));
    }

    private boolean clientePossuiDebito(Cliente cliente) {
        if (cliente.getLocacoes() != null) {
            for (Locacao locacao : cliente.getLocacoes()) {
                if (!locacao.isPago()) { // Verifica se locacao nao foi paga
                    return true;
                }
            }
        }
        return false;
    }

    private boolean itemJaLocado(Item item) {
        if (item.getLocacoes() != null) {
            for (Locacao locacao : item.getLocacoes()) {
                if (!locacao.isPago()) { // Verifica se locacao nao foi paga
                    return true;
                }
            }
        }
        return false;
    }

}
