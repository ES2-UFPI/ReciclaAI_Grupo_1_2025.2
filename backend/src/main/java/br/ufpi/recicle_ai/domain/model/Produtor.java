package br.ufpi.recicle_ai.domain.model;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.recicle_ai.domain.model.item.ItemInventario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;

@Getter
@Setter
@Entity
public class Produtor extends Agente {
    // Atributos futuros podem ser adicionados aqui

    @OneToMany(mappedBy = "produtor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("produtor") // <-- Corrigido!
    private List<ItemInventario> itens = new ArrayList<>();

    public Produtor() {
    }

    public Produtor(Long id, String nome, String cpf, String cnpj) {
        super(id, nome, TipoAgenteEnum.PESSOA_FISICA, cpf, cnpj);
    }

    
    
}
