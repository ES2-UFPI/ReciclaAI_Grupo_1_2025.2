package br.ufpi.recicle_ai.domain.model.coleta;

import java.time.LocalDateTime;
import java.util.List;

import br.ufpi.recicle_ai.domain.model.Coletor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_coleta")
@AllArgsConstructor
@NoArgsConstructor
public class Coleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "coletor_id", nullable = false)
    private Coletor coletor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    private PontoColeta pontoColeta;
    @OneToMany(mappedBy = "coleta")
    private List<ItemColeta> itensColeta;
}
