package br.ufpi.recicle_ai.domain.form.beneficiamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemBeneficiamentoForm {
    @NotNull
    @Positive
    private Integer quantidadeMinima;

    @NotNull
    private Long beneficiamentoId;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private Long itemId;
}
