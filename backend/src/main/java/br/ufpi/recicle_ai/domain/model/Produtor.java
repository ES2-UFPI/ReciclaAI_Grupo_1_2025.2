package br.ufpi.recicle_ai.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @OneToMany(mappedBy = "produtor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("produtor") // <-- Corrigido!
    private List<Itens> itens = new ArrayList<>();

    public Produtor() {
    }

    public Produtor(Long id, String nome, String cpf, String cnpj) {
        super(id, nome, TipoAgenteEnum.PRODUTOR, cpf, cnpj);
    }

    
    
}
