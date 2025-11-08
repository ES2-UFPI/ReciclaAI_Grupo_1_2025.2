package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.form.ColetorForm;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import br.ufpi.recicle_ai.domain.form.coleta.ColetaForm;

@Mapper(componentModel = "spring", uses = {ColetorMapper.class, PontoColetaMapper.class, ItemColetaMapper.class})
public interface ColetaMapper {

    ColetaMapper INSTANCE = Mappers.getMapper(ColetaMapper.class);

    ColetaDTO toDTO(Coleta coleta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coletor", ignore = true)
    @Mapping(target = "pontoColeta", ignore = true)
    Coleta toModel(ColetaForm coletorForm);
}
