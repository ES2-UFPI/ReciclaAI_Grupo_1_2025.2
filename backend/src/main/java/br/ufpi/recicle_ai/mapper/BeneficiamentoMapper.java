package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ReceptorMapper.class, PontoColetaMapper.class, ItemBeneficiamentoMapper.class})
public interface BeneficiamentoMapper {

    BeneficiamentoMapper INSTANCE = Mappers.getMapper(BeneficiamentoMapper.class);

    BeneficiamentoDTO toDTO(Beneficiamento beneficiamento);
}
