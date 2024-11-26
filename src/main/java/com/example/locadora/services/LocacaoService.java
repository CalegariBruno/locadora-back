package com.example.locadora.services;

import com.example.locadora.domain.Locacao;
import com.example.locadora.repositories.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;
    
    public Locacao salvar(Locacao locacao) {
        verificarItemNaoNulo(locacao);
        //verificarClienteNaoNulo(locacao)
        return locacaoRepository.save(locacao);
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

            verificarItemNaoNulo(locacao);
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

    private void verificarItemNaoNulo(Locacao locacao) {
        if (locacao.getItem() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A locação deve estar associada a um item.");
        }
    }
}
