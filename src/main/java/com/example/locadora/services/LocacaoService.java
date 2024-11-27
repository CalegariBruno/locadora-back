package com.example.locadora.services;

import com.example.locadora.domain.Classe;
import com.example.locadora.domain.Cliente;
import com.example.locadora.domain.Item;
import com.example.locadora.domain.Locacao;
import com.example.locadora.dtos.LocacaoDTO;
import com.example.locadora.repositories.*;
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
    
//    public Locacao salvar(Locacao locacao) {
//        verificarItemNaoNulo(locacao);
//        verificarClienteNaoNulo(locacao);
//        return locacaoRepository.save(locacao);
//    }

    public Locacao efetuarLocacao(LocacaoDTO locacao){

        Item item = itemRepository.findById(locacao.item().getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!"));

        Cliente cliente = clienteRepository.findById(locacao.cliente().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));

        // Verifica se o cliente está em débito
        if (clientePossuiDebito(cliente)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente está em débito e não pode realizar a locação.");
        }

        // Obtém a classe do título associado ao item
        Classe classe = item.getTitulo().getClasse();

        // Calcula o valor da locação e a data de devolução prevista
        Double valorLocacao = classe.getValor();
        LocalDate dataLocacao = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataLocacao.plusDays(classe.getPrazoDevolucao());

        // Permite alteração pelo funcionário (se aplicável)
        if (locacao.valorAlterado() != null) {
            valorLocacao = locacao.valorAlterado();
        }

        if (locacao.dtDevolucaoAlterado() != null) {
            dataDevolucaoPrevista = locacao.dtDevolucaoAlterado();
        }

        // Cria uma nova locação
        Locacao novaLocacao = new Locacao();
        novaLocacao.setDtLocacao(dataLocacao);
        novaLocacao.setDtDevolucaoPrevista(dataDevolucaoPrevista);
        novaLocacao.setValorCobrado(valorLocacao);
        novaLocacao.setItem(item);
        novaLocacao.setCliente(cliente);

        // Salva a locação no repositório
        return locacaoRepository.save(novaLocacao);
    }

    public Locacao editar(Long id, Locacao locacaoAtualizada) {

        Optional<Locacao> locacaoExistente = locacaoRepository.findById(id);

        if (locacaoExistente.isPresent()) {
            Locacao locacao = locacaoExistente.get();
            locacao.setDtLocacao(locacaoAtualizada.getDtLocacao());
            locacao.setDtDevolucaoPrevista(locacaoAtualizada.getDtDevolucaoPrevista());
            locacao.setDtDevolucaoEfetiva(locacaoAtualizada.getDtDevolucaoEfetiva());
            locacao.setValorCobrado(locacaoAtualizada.getValorCobrado());
            locacao.setMultaCobrada(locacaoAtualizada.getMultaCobrada());
            locacao.setItem(locacaoAtualizada.getItem());
            locacao.setCliente(locacaoAtualizada.getCliente());
//
//            verificarItemNaoNulo(locacao);
//            verificarClienteNaoNulo(locacao);
            return locacaoRepository.save(locacao);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locação não encontrada!");
        }
    }

    public void deletar(Long id) throws Exception {

        Optional<Locacao> locacao = locacaoRepository.findById(id);

        if (locacao.isPresent()) {            
            locacaoRepository.delete(locacao.get());
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
        // Lógica para verificar se o cliente possui débito
        // Por exemplo: consultar locações anteriores do cliente que não foram quitadas
        return false;
    }

//    private void verificarItemNaoNulo(Locacao locacao) {
//        if (locacao.getItem() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A locação deve estar associada a um item.");
//        }
//    }
//
//    private void verificarClienteNaoNulo(Locacao locacao) {
//        if (locacao.getCliente() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A locação deve estar associada a um cliente.");
//        }
//    }
}
