package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemBeneficiamento;
import br.ufpi.recicle_ai.mapper.ItemBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.ItemBeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemBeneficiamentoService {

    private final ItemBeneficiamentoRepository itemBeneficiamentoRepository;
    private final BeneficiamentoService beneficiamentoService;
    private final ItemService itemService;
    private final ItemBeneficiamentoMapper itemBeneficiamentoMapper;

    @Transactional
    public ItemBeneficiamentoDTO create(ItemBeneficiamentoForm form) {
        Beneficiamento beneficiamento = beneficiamentoService.findEntityById(form.getBeneficiamentoId());
        Item item = itemService.buscarPorId(form.getItemId());
        ItemBeneficiamento itemBeneficiamento = itemBeneficiamentoMapper.toModel(form);
        itemBeneficiamento.setBeneficiamento(beneficiamento);
        itemBeneficiamento.setItem(item);
        ItemBeneficiamento itemSalvo = itemBeneficiamentoRepository.save(itemBeneficiamento);

        return itemBeneficiamentoMapper.toDTO(itemSalvo);
    }
}
