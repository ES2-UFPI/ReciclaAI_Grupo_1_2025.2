package br.ufpi.recicle_ai.domain.dto.eventoColeta;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEventoColetaDTO {
    private Long id;
    private Integer quantidade;
    private ItemDTO item;
}
