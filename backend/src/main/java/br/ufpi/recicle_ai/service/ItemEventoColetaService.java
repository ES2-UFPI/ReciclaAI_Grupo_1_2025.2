package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.ItemEventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.ItemEventoColetaForm;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.domain.model.coleta.ItemColeta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.ItemEventoColetaMapper;
import br.ufpi.recicle_ai.repository.ItemColetaRepository;
import br.ufpi.recicle_ai.repository.ItemEventoColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ItemEventoColetaService {

    private final ItemEventoColetaRepository itemEventoColetaRepository;
    private final ItemColetaRepository itemColetaRepository;
    private final EventoColetaService eventoColetaService;
    private final ItemService itemService;
    private final ItemInventarioService itemInventarioService;
    private final ItemEventoColetaMapper itemEventoColetaMapper;

    @Transactional
    public ItemEventoColetaDTO create(ItemEventoColetaForm form) {
        EventoColeta eventoColeta = eventoColetaService.findEntityById(form.getEventoColetaId());
        Item item = itemService.buscarPorId(form.getItemId());

        // 1. Validações de negócio
        validarRegrasDoItem(eventoColeta, item, form.getQuantidade());

        // 2. Debita a quantidade do inventário do produtor
        Long produtorId = eventoColeta.getProdutor().getId();
        BigDecimal quantidadeParaDebitar = new BigDecimal(form.getQuantidade());
        itemInventarioService.debitarDoInventario(produtorId, item.getId(), quantidadeParaDebitar);

        // 3. Cria o registro do item no evento de coleta
        ItemEventoColeta itemEventoColeta = itemEventoColetaMapper.toModel(form);
        itemEventoColeta.setEventoColeta(eventoColeta);
        itemEventoColeta.setItem(item);

        itemEventoColeta = itemEventoColetaRepository.save(itemEventoColeta);
        return itemEventoColetaMapper.toDTO(itemEventoColeta);
    }

    private void validarRegrasDoItem(EventoColeta eventoColeta, Item item, Integer quantidade) {
        // Valida se a quantidade atinge o mínimo da coleta, buscando na lista já carregada
        ItemColeta regraItemColeta = eventoColeta.getColeta().getItensColeta().stream()
                .filter(i -> i.getItem().equals(item))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Este item não é aceito nesta coleta."));

        if (quantidade < regraItemColeta.getQuantidadeMinima()) {
            throw new RegraDeNegocioException("A quantidade informada (" + quantidade + ") é menor que a quantidade mínima exigida para " + item.getNome() +
                    " (" + regraItemColeta.getQuantidadeMinima() + ").");
        }
    }
}
