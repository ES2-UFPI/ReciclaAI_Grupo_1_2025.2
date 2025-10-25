package br.ufpi.recicle_ai.domain.model.eventoColeta;

import br.ufpi.recicle_ai.domain.model.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_item_evento_coleta")
@AllArgsConstructor
@NoArgsConstructor
public class ItemEventoColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "evento_coleta_id", nullable = false)
    private EventoColeta eventoColeta;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
