package br.ufpi.recicle_ai.domain.model;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Agente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoAgenteEnum tipoAgente;
    private String cpf;
    private String cnpj;
}
