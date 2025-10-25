package br.ufpi.recicle_ai.domain.form.item;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItemInventarioForm {
    private BigDecimal quantidade;
    private Long itemId;
    private Long pessoaId;
    private TipoPessoaEnum tipoPessoa;
}
