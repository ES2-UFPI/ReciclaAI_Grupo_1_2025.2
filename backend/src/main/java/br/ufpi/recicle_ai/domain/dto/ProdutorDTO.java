package br.ufpi.recicle_ai.domain.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProdutorDTO extends AgenteDTO {
    private BigDecimal saldoMoedasVerdes;
}
