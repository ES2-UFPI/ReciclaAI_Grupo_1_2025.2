package br.ufpi.recicle_ai.domain.dto.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemEventoBeneficiamentoDTO {
    private Long id;
    private Integer quantidade;
    private ItemDTO item;
}
