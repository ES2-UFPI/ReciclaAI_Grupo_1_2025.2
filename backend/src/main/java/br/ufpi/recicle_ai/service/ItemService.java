package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.mapper.ItemMapper;
import br.ufpi.recicle_ai.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Transactional(readOnly = true)
    public Item buscarPorId(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }
}
