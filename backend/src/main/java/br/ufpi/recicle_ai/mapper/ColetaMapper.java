package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ColetorMapper.class, PontoColetaMapper.class, ItemColetaMapper.class})
public interface ColetaMapper {

    ColetaMapper INSTANCE = Mappers.getMapper(ColetaMapper.class);

    ColetaDTO toDTO(Coleta coleta);
}
