package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.BeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ReceptorMapper.class, PontoColetaMapper.class, ItemBeneficiamentoMapper.class})
public interface BeneficiamentoMapper {

    BeneficiamentoMapper INSTANCE = Mappers.getMapper(BeneficiamentoMapper.class);

    BeneficiamentoDTO toDTO(Beneficiamento beneficiamento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receptor", ignore = true)
    @Mapping(target = "pontoColeta", ignore = true)
    Beneficiamento toModel(BeneficiamentoForm form);
}
