package br.ufpi.recicle_ai.domain.form.beneficiamento;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventoBeneficiamentoForm {
    @NotNull
    private Long beneficiamentoId;

    @NotNull
    private Long coletorId;
}
