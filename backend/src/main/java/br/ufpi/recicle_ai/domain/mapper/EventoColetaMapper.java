package br.ufpi.recicle_ai.domain.mapper;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.ufpi.recicle_ai.domain.model.EventoColeta;

@Mapper(componentModel = "spring")
public interface EventoColetaMapper {

    EventoColetaMapper INSTANCE = Mappers.getMapper(EventoColetaMapper.class);

    AgenteDTO.EventoColetaResponseDTO toDTO(EventoColeta evento);
}

   