package br.ufpi.recicle_ai.domain.mapper;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.model.item.ItemInventario;
import br.ufpi.recicle_ai.domain.form.ItensForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemInventarioMapper {

    ItemInventario INSTANCE = Mappers.getMapper(ItemInventario.class);

    public AgenteDTO.ItensDTO toDTO(ItemInventario item);

    @Mapping(target = "id", ignore = true)
    ItemInventario toItens(AgenteDTO.ItensDTO itensDTO);
    
    public ItemInventario fromForm(ItensForm form);
}
