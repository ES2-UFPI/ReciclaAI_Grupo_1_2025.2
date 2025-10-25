package br.ufpi.recicle_ai.domain.model.coleta;

import br.ufpi.recicle_ai.domain.model.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_item_coleta")
@AllArgsConstructor
@NoArgsConstructor
public class ItemColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidadeMinima;
    @ManyToOne
    @JoinColumn(name = "coleta_id", nullable = false)
    private Coleta coleta;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
