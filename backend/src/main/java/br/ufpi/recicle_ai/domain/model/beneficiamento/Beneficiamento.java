package br.ufpi.recicle_ai.domain.model.beneficiamento;

import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_beneficiamento")
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    private Receptor receptor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    private PontoColeta pontoColeta;
    @OneToMany(mappedBy = "beneficiamento")
    private List<ItemBeneficiamento> itensBeneficiamento;
}
