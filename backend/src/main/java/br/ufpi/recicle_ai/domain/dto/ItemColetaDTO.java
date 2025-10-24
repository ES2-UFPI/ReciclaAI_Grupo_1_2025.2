package br.ufpi.recicle_ai.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemColetaDTO {
    private Long id;
    private Long itemId;
    private String nomeItem;
    private String unidadeItem;
    private Integer qtdMinima;
}
