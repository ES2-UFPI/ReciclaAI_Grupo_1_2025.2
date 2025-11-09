package br.ufpi.recicle_ai.dto;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private Long pessoaId;
    private TipoPessoaEnum tipoPessoa;
    private String nome;
}