package br.ufpi.recicle_ai.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventoColetaColetorDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String tipo;
}
