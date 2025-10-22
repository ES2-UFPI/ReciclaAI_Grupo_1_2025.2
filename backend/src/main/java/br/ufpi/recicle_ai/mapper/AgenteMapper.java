package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.model.Agente;
import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.form.AgenteForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AgenteMapper {

    AgenteMapper INSTANCE = Mappers.getMapper(AgenteMapper.class);

    AgenteDTO toDTO(Agente agente);

    @Mapping(target = "id", ignore = true)
    Agente toModel(AgenteForm agenteForm);
}
