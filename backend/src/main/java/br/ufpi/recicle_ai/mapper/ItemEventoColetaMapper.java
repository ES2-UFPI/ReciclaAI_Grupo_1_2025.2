package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.ItemEventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.ItemEventoColetaForm;
import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EventoColetaMapper.class, ItemMapper.class})
public interface ItemEventoColetaMapper {

    ItemEventoColetaMapper INSTANCE = Mappers.getMapper(ItemEventoColetaMapper.class);

    ItemEventoColetaDTO toDTO(ItemEventoColeta itemEventoColeta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventoColeta", ignore = true)
    @Mapping(target = "item", ignore = true)
    ItemEventoColeta toModel(ItemEventoColetaForm form);
}
