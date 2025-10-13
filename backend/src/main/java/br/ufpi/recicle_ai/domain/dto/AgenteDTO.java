package br.ufpi.recicle_ai.domain.dto;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import lombok.Data;

@Data
public class AgenteDTO {
    private Long id;
    private String nome;
    private TipoAgenteEnum tipoAgente;
    private String cpf;
    private String cnpj;
}
