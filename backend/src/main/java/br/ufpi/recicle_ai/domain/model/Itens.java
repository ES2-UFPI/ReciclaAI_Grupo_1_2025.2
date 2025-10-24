package br.ufpi.recicle_ai.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
@Entity
public class Itens {

    @ManyToOne 
    @JoinColumn(name = "produtor_id") 
    @JsonIgnore
    // Aponta para a lista na entidade Produtor, impedindo que o Jackson a serialize
    @JsonIgnoreProperties("itens") // <-- Correto!
    private Produtor produtor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String nomeItem;
    private String unidadeItem;
    private Double quantidadeEstoque;


    public Itens(){

    }
    // Construtor manual, pois AllArgsConstructor pode complicar com Object
    public Itens(Long id, String nomeItem, String unidadeItem, Double quantidadeEstoque) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.unidadeItem = unidadeItem;
        this.quantidadeEstoque = quantidadeEstoque;


        if ("unidade".equalsIgnoreCase(unidadeItem) && quantidadeEstoque != null) {
             // Força o valor inteiro, mas o tipo JPA é Double/Float
             this.quantidadeEstoque = (double) quantidadeEstoque.intValue();
        }
    }

    public void setValor(Object novoValor) {
        if ("unidade".equalsIgnoreCase(this.unidadeItem)) {
            if (novoValor instanceof Number) {
                this.quantidadeEstoque = (double) ((Number) novoValor).intValue();
            } else {
                // Lidar com erro
                throw new IllegalArgumentException("Valor deve ser um inteiro para unidadeItem 'unidade'.");
            }
        } else {
            if (novoValor instanceof Number) {
                this.quantidadeEstoque = ((Number) novoValor).doubleValue();
            } else {
                // Lidar com erro
                throw new IllegalArgumentException("Valor deve ser um double para outros tipos.");
            }
        }
    }

    
}
