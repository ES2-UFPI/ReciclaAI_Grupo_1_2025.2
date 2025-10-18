package br.ufpi.recicle_ai.domain.model.dto;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.model.Capacidade;
import br.ufpi.recicle_ai.domain.model.Cobertura;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ColetorDTO extends AgenteDTO {
    // Atributos futuros podem ser adicionados aqui

    private String tipoColetor;
    private Cobertura cobertura;
    private Capacidade capacidade;
}
