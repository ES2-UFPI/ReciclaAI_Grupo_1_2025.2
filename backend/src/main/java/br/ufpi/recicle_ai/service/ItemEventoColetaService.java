package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.ItemEventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.ItemEventoColetaForm;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import br.ufpi.recicle_ai.mapper.ItemEventoColetaMapper;
import br.ufpi.recicle_ai.repository.ItemEventoColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemEventoColetaService {

    private final ItemEventoColetaRepository itemEventoColetaRepository;
    private final EventoColetaService eventoColetaService;
    private final ItemService itemService;
    private final ItemEventoColetaMapper itemEventoColetaMapper;

    @Transactional
    public ItemEventoColetaDTO create(ItemEventoColetaForm form) {
        EventoColeta eventoColeta = eventoColetaService.findEntityById(form.getEventoColetaId());
        Item item = itemService.buscarPorId(form.getItemId());

        ItemEventoColeta itemEventoColeta = itemEventoColetaMapper.toModel(form);
        itemEventoColeta.setEventoColeta(eventoColeta);
        itemEventoColeta.setItem(item);

        itemEventoColeta = itemEventoColetaRepository.save(itemEventoColeta);
        return itemEventoColetaMapper.toDTO(itemEventoColeta);
    }
}
