package br.ufpi.recicle_ai.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PontoColetaDTO {
    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
}
