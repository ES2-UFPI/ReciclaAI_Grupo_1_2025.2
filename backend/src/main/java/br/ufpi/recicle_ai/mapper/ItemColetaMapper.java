package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.coleta.ItemColetaDTO;
import br.ufpi.recicle_ai.domain.form.coleta.ItemColetaForm;
import br.ufpi.recicle_ai.domain.model.coleta.ItemColeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface ItemColetaMapper {

    ItemColetaMapper INSTANCE = Mappers.getMapper(ItemColetaMapper.class);

    ItemColetaDTO toDTO(ItemColeta itemColeta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coleta", ignore = true)
    @Mapping(target = "item", ignore = true)
    ItemColeta toModel(ItemColetaForm form);
}
