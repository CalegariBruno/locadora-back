package com.example.locadora.services;

import com.example.locadora.domain.Item;
import com.example.locadora.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item salvar(Item item) {
        return itemRepository.save(item);
    }

    public Item editar(Long id, Item itemAtualizado) {

        Optional<Item> itemExistente = itemRepository.findById(id);

        if (itemExistente.isPresent()) {
            Item item = itemExistente.get();
            item.setNumSerie(itemAtualizado.getNumSerie());
            item.setDtAquisicao(itemAtualizado.getDtAquisicao());
            item.setTipoItem(itemAtualizado.getTipoItem());
            item.setTitulo(itemAtualizado.getTitulo());
            return itemRepository.save(item);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!");
        }
    }

    public void deletar(Long id) {

        Optional<Item> item = itemRepository.findById(id);

        if (item.isPresent()) {
            itemRepository.delete(item.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!");
        }
    }

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Item buscarPorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado!"));
    }

}
