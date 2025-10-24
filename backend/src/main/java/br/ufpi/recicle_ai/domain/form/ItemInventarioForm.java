package br.ufpi.recicle_ai.domain.form;

import br.ufpi.recicle_ai.domain.model.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.model.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
