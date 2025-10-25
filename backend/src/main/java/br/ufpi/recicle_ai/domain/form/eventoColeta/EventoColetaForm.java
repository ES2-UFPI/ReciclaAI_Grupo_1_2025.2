package br.ufpi.recicle_ai.domain.form.eventoColeta;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoColetaForm {
    @NotNull
    private Long coletaId;
    @NotNull
    private Long produtorId;
}
