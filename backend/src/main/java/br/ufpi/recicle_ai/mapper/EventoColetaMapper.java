package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.EventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.EventoColetaForm;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ColetaMapper.class, ProdutorMapper.class})
public interface EventoColetaMapper {

    EventoColetaMapper INSTANCE = Mappers.getMapper(EventoColetaMapper.class);

    EventoColetaDTO toDTO(EventoColeta eventoColeta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coleta", ignore = true)
    @Mapping(target = "produtor", ignore = true)
    @Mapping(target = "status", ignore = true)
    EventoColeta toModel(EventoColetaForm eventoColetaForm);
}
