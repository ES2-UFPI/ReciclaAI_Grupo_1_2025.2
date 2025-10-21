package br.ufpi.recicle_ai.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItensDTO {
    private Long id;
    private String nomeItem;
    private String unidadeItem;
    private Double quantidadeEstoque;
}
