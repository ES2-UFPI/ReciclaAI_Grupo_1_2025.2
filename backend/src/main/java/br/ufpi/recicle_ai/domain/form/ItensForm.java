package br.ufpi.recicle_ai.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItensForm {
    
    private Long id;
    private String nomeItem;
    private String unidadeItem;
    private Double quantidadeEstoque;
}
