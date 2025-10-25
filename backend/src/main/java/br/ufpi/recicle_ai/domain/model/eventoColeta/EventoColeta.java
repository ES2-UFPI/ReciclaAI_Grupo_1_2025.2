package br.ufpi.recicle_ai.domain.model.eventoColeta;

import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_evento_coleta")
public class EventoColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "coleta_id", nullable = false)
    private Coleta coleta;
    @ManyToOne
    @JoinColumn(name = "produtor_id", nullable = false)
    private Produtor produtor;
    @Enumerated(EnumType.STRING)
    private StatusEventoColetaEnum status;
    @OneToMany(mappedBy = "eventoColeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEventoColeta> itens;
}
