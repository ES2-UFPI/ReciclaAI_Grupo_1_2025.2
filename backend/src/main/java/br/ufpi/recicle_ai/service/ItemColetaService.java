package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.coleta.ItemColetaDTO;
import br.ufpi.recicle_ai.domain.form.coleta.ItemColetaForm;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.domain.model.coleta.ItemColeta;
import br.ufpi.recicle_ai.mapper.ItemColetaMapper;
import br.ufpi.recicle_ai.repository.ItemColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemColetaService {

    private final ItemColetaRepository itemColetaRepository;
    private final ColetaService coletaService;
    private final ItemService itemService;
    private final ItemColetaMapper itemColetaMapper;

    @Transactional
    public ItemColetaDTO create(ItemColetaForm form) {
        Coleta coleta = coletaService.findEntityById(form.getColetaId());
        Item item = itemService.buscarPorId(form.getItemId());

        ItemColeta itemColeta = itemColetaMapper.toModel(form);
        itemColeta.setColeta(coleta);
        itemColeta.setItem(item);

        itemColeta = itemColetaRepository.save(itemColeta);
        return itemColetaMapper.toDTO(itemColeta);
    }
}
