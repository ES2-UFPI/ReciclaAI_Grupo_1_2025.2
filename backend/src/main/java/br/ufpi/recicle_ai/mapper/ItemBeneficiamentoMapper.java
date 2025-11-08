package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemBeneficiamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ItemBeneficiamentoMapper {

    ItemBeneficiamentoMapper INSTANCE = Mappers.getMapper(ItemBeneficiamentoMapper.class);

    ItemBeneficiamentoDTO toDTO(ItemBeneficiamento itemBeneficiamento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "beneficiamento", ignore = true)
    @Mapping(target = "item", ignore = true)
    ItemBeneficiamento toModel(ItemBeneficiamentoForm form);
}
