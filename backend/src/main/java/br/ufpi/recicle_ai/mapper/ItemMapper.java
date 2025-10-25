package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import br.ufpi.recicle_ai.domain.model.item.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDTO toDTO(Item item);
}
