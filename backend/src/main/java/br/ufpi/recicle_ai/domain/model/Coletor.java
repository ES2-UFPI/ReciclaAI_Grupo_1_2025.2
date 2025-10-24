package br.ufpi.recicle_ai.domain.model;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Coletor extends Agente {


    private String tipoDeColeta;
    
    // Coletor.java (Modelo)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cobertura_id")
    private Cobertura cobertura;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "capacidade_id")
    private Capacidade capacidade;

    public Coletor() {
        super();
    }
    
    // Construtor com super (ajuste os parâmetros conforme Agente)
    public Coletor(Long id, String nome, String cpf, String cnpj,String tipoDeColeta) {
        super(id, nome, TipoAgenteEnum.PESSOA_FISICA, cpf, cnpj);
        this.tipoDeColeta = tipoDeColeta;
    }
}
