package br.ufpi.recicle_ai.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import br.ufpi.recicle_ai.domain.model.Itens;

@Getter
@Setter
@Entity
public class Produtor extends Agente {
    // Atributos futuros podem ser adicionados aqui

    @CollectionTable(name = "itens")
    @OneToMany(mappedBy = "produtor", cascade = CascadeType.ALL)
    private List<Itens> itens = new ArrayList<>();

    public Produtor() {
    }

    public Produtor(Long id, String nome, String cpf, String cnpj) {
        super(id, nome, TipoAgenteEnum.PRODUTOR, cpf, cnpj);
    }

    
    
}
