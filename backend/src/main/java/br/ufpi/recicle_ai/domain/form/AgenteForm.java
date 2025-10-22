package br.ufpi.recicle_ai.domain.form;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgenteForm {

    @NotBlank
    private String nome;

    @NotNull
    private TipoAgenteEnum tipoAgente;

    private String cpf;

    private String cnpj;
}
