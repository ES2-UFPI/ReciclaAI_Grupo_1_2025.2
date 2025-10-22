package br.ufpi.recicle_ai.domain.mapper;

import br.ufpi.recicle_ai.domain.model.Itens;
import br.ufpi.recicle_ai.domain.model.dto.ItensDTO;
import br.ufpi.recicle_ai.domain.model.dto.form.ItensForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItensMapper {

    ItensMapper INSTANCE = Mappers.getMapper(ItensMapper.class);

    public ItensDTO toDTO(Itens item);

    @Mapping(target = "id", ignore = true)
    Itens toItens(ItensDTO itensDTO);
    
    public Itens fromForm(ItensForm form);
}
