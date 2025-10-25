package br.ufpi.recicle_ai.domain.dto.item;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventarioDTO {
    private Long id;
    private BigDecimal quantidade;
    private ItemDTO item;
    private Long pessoaId;
    private TipoPessoaEnum tipoPessoa;
}
