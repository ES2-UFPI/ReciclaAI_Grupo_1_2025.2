package br.ufpi.recicle_ai.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.ufpi.recicle_ai.domain.model.EventoColeta;
import br.ufpi.recicle_ai.domain.model.dto.EventoColetaResponseDTO;

@Mapper(componentModel = "spring")
public interface EventoColetaMapper {

    EventoColetaMapper INSTANCE = Mappers.getMapper(EventoColetaMapper.class);

    EventoColetaResponseDTO toDTO(EventoColeta evento);
}

   