package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class Itens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String nomeItem;
    private String unidadeItem;
    private Object quantidadeEstoque;

    // Construtor manual, pois AllArgsConstructor pode complicar com Object
    public Itens(Long id, String nomeItem, String unidadeItem, Object quantidadeEstoque) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.unidadeItem = unidadeItem;
        this.quantidadeEstoque = quantidadeEstoque;


    // Lógica de validação no construtor
        if ("unidade".equalsIgnoreCase(unidadeItem) && !(quantidadeEstoque instanceof Integer)) {
            // Pode lançar exceção ou tentar converter
            if (quantidadeEstoque instanceof Number) {
                this.quantidadeEstoque = ((Number) quantidadeEstoque).intValue();
            } else {
                throw new IllegalArgumentException("Valor deve ser um inteiro para unidadeItem 'unidade'.");
            }
        } else if (!"unidade".equalsIgnoreCase(unidadeItem) && !(quantidadeEstoque instanceof Double)) {
            // Pode lançar exceção ou tentar converter
             if (quantidadeEstoque instanceof Number) {
                this.quantidadeEstoque = ((Number) quantidadeEstoque).doubleValue();
            } else {
                throw new IllegalArgumentException("Valor deve ser um double para unidadeItem diferente de 'unidade'.");
            }
        }
    }

    public void setValor(Object novoValor) {
        if ("unidade".equalsIgnoreCase(this.unidadeItem)) {
            if (novoValor instanceof Number) {
                this.quantidadeEstoque = ((Number) novoValor).intValue();
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

    // Você também pode querer adicionar um método auxiliar para obter o valor
    public Number getValorNumerico() {
        return (Number) this.quantidadeEstoque;
    }
}
