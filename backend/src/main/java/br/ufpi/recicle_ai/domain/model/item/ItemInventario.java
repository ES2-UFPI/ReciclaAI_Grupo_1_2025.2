package br.ufpi.recicle_ai.domain.model.item;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "tb_item_inventario")
@AllArgsConstructor
@NoArgsConstructor
public class ItemInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private BigDecimal quantidade;
    @ManyToOne
    private Item item;
    private Long pessoaId;
    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipoPessoa;
}
