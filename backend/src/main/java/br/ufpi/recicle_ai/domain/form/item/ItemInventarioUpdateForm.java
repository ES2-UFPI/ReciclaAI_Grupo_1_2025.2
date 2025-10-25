package br.ufpi.recicle_ai.domain.form.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemInventarioUpdateForm {
    @NotNull
    @PositiveOrZero
    private BigDecimal quantidade;
}
