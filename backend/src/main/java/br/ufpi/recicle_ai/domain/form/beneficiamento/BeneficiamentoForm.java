package br.ufpi.recicle_ai.domain.form.beneficiamento;

import java.time.LocalDateTime;

import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BeneficiamentoForm {
    @NotNull
    private Long receptorId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @NotNull
    private PontoColeta pontoColeta;
}
