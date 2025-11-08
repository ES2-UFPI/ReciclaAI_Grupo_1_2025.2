package br.ufpi.recicle_ai.domain.form.beneficiamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemEventoBeneficiamentoForm {
    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    private Long eventoBeneficiamentoId;

    @NotNull
    private Long itemId;
}
