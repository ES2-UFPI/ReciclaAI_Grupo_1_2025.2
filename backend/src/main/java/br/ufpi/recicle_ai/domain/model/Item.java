package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "unidade", nullable = false)
    private String unidade;
    
    @Column(name = "valor_unitario", precision = 10, scale = 2)
    private BigDecimal valorUnitario;
    
    @Column(name = "descricao")
    private String descricao;
}