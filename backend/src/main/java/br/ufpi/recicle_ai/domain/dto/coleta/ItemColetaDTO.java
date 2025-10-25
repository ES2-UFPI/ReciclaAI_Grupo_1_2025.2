package br.ufpi.recicle_ai.domain.dto.coleta;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemColetaDTO {
    private Long id;
    private Integer quantidadeMinima;
    private ItemDTO item;
}
