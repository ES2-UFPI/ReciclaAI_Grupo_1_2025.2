package br.ufpi.recicle_ai.domain.form;

import br.ufpi.recicle_ai.domain.model.Capacidade;
import br.ufpi.recicle_ai.domain.model.Cobertura;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ColetorForm extends AgenteForm {

    private String tipoDeColeta;
    private Cobertura cobertura;
    private Capacidade capacidade;


}
