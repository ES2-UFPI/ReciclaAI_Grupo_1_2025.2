package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Item buscarPorId(Long id) {
        return itemRepository.findById(id).orElse(null);
    }
}
