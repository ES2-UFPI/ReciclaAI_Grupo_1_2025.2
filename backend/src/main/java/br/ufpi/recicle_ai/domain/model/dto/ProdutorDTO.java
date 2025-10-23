package br.ufpi.recicle_ai.domain.model.dto;

import java.util.List;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.model.Itens;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProdutorDTO extends AgenteDTO {
    private List<Itens> itens;

}
