package br.ufpi.recicle_ai.mapper;

import br.ufpi.recicle_ai.domain.dto.ItemInventarioDTO;
import br.ufpi.recicle_ai.domain.model.item.ItemInventario;
import br.ufpi.recicle_ai.domain.form.ItemInventarioForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemInventarioMapper {

    ItemInventario INSTANCE = Mappers.getMapper(ItemInventario.class);

    public ItemInventarioDTO toDTO(ItemInventario item);
    
    public ItemInventario fromForm(ItemInventarioForm form);
}
