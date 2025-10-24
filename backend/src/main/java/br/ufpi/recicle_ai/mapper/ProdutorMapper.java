package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.form.ProdutorForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AgenteMapper.class)
public interface ProdutorMapper {

    ProdutorMapper INSTANCE = Mappers.getMapper(ProdutorMapper.class);

    AgenteDTO.ProdutorDTO toDTO(Produtor produtor);

    @Mapping(target = "id", ignore = true)
    Produtor toModel(ProdutorForm produtorForm);

}
