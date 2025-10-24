package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.form.ColetorForm;
import br.ufpi.recicle_ai.domain.model.Coletor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ColetorMapper {
    ColetorMapper INSTANCE = Mappers.getMapper(ColetorMapper.class);

    ColetorDTO toDTO(Coletor coletor);

    @Mapping(target = "id", ignore = true)
    Coletor toModel(ColetorForm coletorForm);
}
