package br.ufpi.recicle_ai.domain.form.beneficiamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemBeneficiamentoForm {
    @NotNull
    @Positive
    private Integer quantidadeMinima;

    @NotNull
    private Long beneficiamentoId;

    @NotNull
    private Long itemId;
}
