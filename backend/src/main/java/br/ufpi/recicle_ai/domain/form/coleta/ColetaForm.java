package br.ufpi.recicle_ai.domain.form.coleta;

import java.time.LocalDateTime;
import java.util.List;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.dto.coleta.ItemColetaDTO;
import br.ufpi.recicle_ai.domain.dto.coleta.PontoColetaDTO;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColetaForm {
    
    @NotNull
    private Long coletorId;
    @NotNull
    @Future(message = "A data de início deve estar no futuro")
    private LocalDateTime dataInicio;
    @NotNull
    @Future(message = "A data de fim deve estar no futuro")
    private LocalDateTime dataFim;
    @NotNull
    private PontoColeta pontoColeta;
}
