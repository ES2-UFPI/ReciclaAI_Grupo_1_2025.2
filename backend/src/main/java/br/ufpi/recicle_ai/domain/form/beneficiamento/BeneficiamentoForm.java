package br.ufpi.recicle_ai.domain.form.beneficiamento;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BeneficiamentoForm {
    
    @NotNull
    private Long id;
    @NotNull
    private Long receptorId;
    @Future
    private LocalDateTime dataInicio;
    @Future
    private LocalDateTime dataFim;
    @NotNull
    private Long pontoColetaId;
}
