package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BeneficiamentoMapper.class, ColetorMapper.class, ItemEventoBeneficiamentoMapper.class})
public interface EventoBeneficiamentoMapper {

    EventoBeneficiamentoMapper INSTANCE = Mappers.getMapper(EventoBeneficiamentoMapper.class);

    EventoBeneficiamentoDTO toDTO(EventoBeneficiamento evento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "beneficiamento", ignore = true)
    @Mapping(target = "coletor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "itens", ignore = true)
    EventoBeneficiamento toModel(EventoBeneficiamentoForm form);
}
