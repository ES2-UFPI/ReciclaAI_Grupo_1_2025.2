package br.ufpi.recicle_ai.domain.dto.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import lombok.Data;

@Data
public class ItemBeneficiamentoDTO {
    private Long id;
    private Integer quantidadeMinima;
    private ItemDTO item;
}
