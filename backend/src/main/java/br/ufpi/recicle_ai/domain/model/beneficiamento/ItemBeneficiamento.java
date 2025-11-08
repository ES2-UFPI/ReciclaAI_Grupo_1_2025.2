package br.ufpi.recicle_ai.domain.model.beneficiamento;

import br.ufpi.recicle_ai.domain.model.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_item_beneficiamento")
@AllArgsConstructor
@NoArgsConstructor
public class ItemBeneficiamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidadeMinima;
    @ManyToOne
    @JoinColumn(name = "beneficiamento_id", nullable = false)
    private Beneficiamento beneficiamento;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
