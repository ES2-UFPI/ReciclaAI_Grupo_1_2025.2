package br.ufpi.recicle_ai.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_coleta")
public class ItemColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento de volta para EventoColeta
    @ManyToOne
    @JoinColumn(name = "evento_coleta_id", nullable = false)
    @JsonIgnoreProperties("itensColeta") // Evita o loop ItemColeta -> EventoColeta
    private EventoColeta eventoColeta; 
    
    // O itemId pode ser a chave estrangeira para a entidade Itens (de inventário), 
    // se você tiver uma tabela de referência de tipos de material
    private Long itemId; 
    
    private String nomeItem;
    
    private String unidadeItem;
    
    private Integer qtdMinima; // O contrato JSON usa 'qtdMinima'

    // Construtores
    public ItemColeta() {
    }
    
    // Construtor com campos
    // ...
}
