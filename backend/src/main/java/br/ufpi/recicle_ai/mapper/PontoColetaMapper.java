package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.coleta.PontoColetaDTO;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PontoColetaMapper {

    PontoColetaMapper INSTANCE = Mappers.getMapper(PontoColetaMapper.class);

    PontoColetaDTO toDTO(PontoColeta pontoColeta);

    PontoColeta toModel(PontoColetaDTO pontoColetaDTO);
}
