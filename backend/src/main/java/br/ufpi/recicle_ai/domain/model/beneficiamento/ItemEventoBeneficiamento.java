package br.ufpi.recicle_ai.domain.model.beneficiamento;

import br.ufpi.recicle_ai.domain.model.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_item_evento_beneficiamento")
@AllArgsConstructor
@NoArgsConstructor
public class ItemEventoBeneficiamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "evento_beneficiamento_id", nullable = false)
    @JsonIgnore
    private EventoBeneficiamento eventoBeneficiamento;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
