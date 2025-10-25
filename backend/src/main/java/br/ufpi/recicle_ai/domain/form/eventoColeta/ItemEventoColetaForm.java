package br.ufpi.recicle_ai.domain.form.eventoColeta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEventoColetaForm {
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull
    private Long eventoColetaId;
    @NotNull
    private Long itemId;
}
