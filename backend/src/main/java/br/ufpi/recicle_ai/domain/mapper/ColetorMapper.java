package br.ufpi.recicle_ai.domain.mapper;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.form.ColetorForm;
import br.ufpi.recicle_ai.mapper.AgenteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AgenteMapper.class)
public interface ColetorMapper {

    ColetorMapper INSTANCE = Mappers.getMapper(ColetorMapper.class);

    AgenteDTO.ColetorDTO toDTO(Coletor coletor);

    @Mapping(target = "id", ignore = true)
    Coletor toModel(ColetorForm coletorForm);

}
