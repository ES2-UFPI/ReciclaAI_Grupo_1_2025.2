package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemEventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemEventoBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemEventoBeneficiamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ItemEventoBeneficiamentoMapper {

    ItemEventoBeneficiamentoMapper INSTANCE = Mappers.getMapper(ItemEventoBeneficiamentoMapper.class);

    ItemEventoBeneficiamentoDTO toDTO(ItemEventoBeneficiamento item);
    ItemEventoBeneficiamento toModel(ItemEventoBeneficiamentoForm form);
}
