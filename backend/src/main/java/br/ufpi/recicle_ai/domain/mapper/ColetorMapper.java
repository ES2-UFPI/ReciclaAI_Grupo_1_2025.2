package br.ufpi.recicle_ai.domain.mapper;

import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.model.dto.form.ColetorForm;
import br.ufpi.recicle_ai.mapper.AgenteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AgenteMapper.class)
public interface ColetorMapper {

    ColetorMapper INSTANCE = Mappers.getMapper(ColetorMapper.class);

    ColetorDTO toDTO(Coletor coletor);

    @Mapping(target = "id", ignore = true)
    Coletor toModel(ColetorForm coletorForm);

}
