package br.ufpi.recicle_ai.domain.mapper;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.domain.form.ReceptorForm;
import br.ufpi.recicle_ai.mapper.AgenteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AgenteMapper.class)
public interface ReceptorMapper {

    ReceptorMapper INSTANCE = Mappers.getMapper(ReceptorMapper.class);

    AgenteDTO.ReceptorDTO toDTO(Receptor receptor);

    @Mapping(target = "id", ignore = true)
    Receptor toModel(ReceptorForm receptorForm);

}
