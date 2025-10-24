package br.ufpi.recicle_ai.domain.dto;

import br.ufpi.recicle_ai.domain.model.Capacidade;
import br.ufpi.recicle_ai.domain.model.Cobertura;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ColetorDTO extends AgenteDTO {
    private String tipoColetor;
    private Cobertura cobertura;
    private Capacidade capacidade;
}
