package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemEventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemEventoBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemBeneficiamento;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemEventoBeneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.ItemEventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.ItemBeneficiamentoRepository;
import br.ufpi.recicle_ai.repository.ItemEventoBeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ItemEventoBeneficiamentoService {

    private final ItemEventoBeneficiamentoRepository itemEventoBeneficiamentoRepository;
    private final ItemBeneficiamentoRepository itemBeneficiamentoRepository;
    private final EventoBeneficiamentoService eventoBeneficiamentoService;
    private final ItemService itemService;
    private final ItemInventarioService itemInventarioService;
    private final ItemEventoBeneficiamentoMapper itemEventoBeneficiamentoMapper;

    @Transactional
    public ItemEventoBeneficiamentoDTO create(ItemEventoBeneficiamentoForm form) {
        EventoBeneficiamento eventoBeneficiamento = eventoBeneficiamentoService.findEntityById(form.getEventoBeneficiamentoId());
        Item item = itemService.buscarPorId(form.getItemId());

        validarRegrasDoItem(eventoBeneficiamento, item, form.getQuantidade());

        Long coletorId = eventoBeneficiamento.getColetor().getId();
        BigDecimal quantidadeParaDebitar = new BigDecimal(form.getQuantidade());
        itemInventarioService.debitarDoInventario(coletorId, TipoPessoaEnum.COLETOR, item.getId(), quantidadeParaDebitar);

        ItemEventoBeneficiamento itemEventoBeneficiamento = itemEventoBeneficiamentoMapper.toModel(form);
        itemEventoBeneficiamento.setEventoBeneficiamento(eventoBeneficiamento);
        itemEventoBeneficiamento.setItem(item);

        itemEventoBeneficiamento = itemEventoBeneficiamentoRepository.save(itemEventoBeneficiamento);
        return itemEventoBeneficiamentoMapper.toDTO(itemEventoBeneficiamento);
    }

    private void validarRegrasDoItem(EventoBeneficiamento evento, Item item, Integer quantidade) {
        ItemBeneficiamento regraItemBeneficiamento = itemBeneficiamentoRepository.findByBeneficiamentoIdAndItemId(evento.getBeneficiamento().getId(), item.getId())
                .orElseThrow(() -> new RegraDeNegocioException("Este item não é aceito neste processo de beneficiamento."));

        if (quantidade < regraItemBeneficiamento.getQuantidadeMinima()) {
            throw new RegraDeNegocioException("A quantidade informada (" + quantidade + ") é menor que a quantidade mínima exigida para este item (" + regraItemBeneficiamento.getQuantidadeMinima() + ").");
        }
    }
}
