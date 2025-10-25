package br.ufpi.recicle_ai.domain.form.coleta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemColetaForm {
    @NotNull
    @Positive
    private Integer quantidadeMinima;
    @NotNull
    private Long coletaId;
    @NotNull
    private Long itemId;
}
