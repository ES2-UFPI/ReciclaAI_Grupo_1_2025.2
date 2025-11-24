package br.ufpi.recicle_ai.domain.model.beneficiamento;

import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.domain.model.Coletor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_evento_beneficiamento")
@AllArgsConstructor
@NoArgsConstructor
public class EventoBeneficiamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beneficiamento_id", nullable = false)
    private Beneficiamento beneficiamento;

    @ManyToOne
    @JoinColumn(name = "coletor_id", nullable = false)
    private Coletor coletor;

    @Enumerated(EnumType.STRING)
    private StatusBeneficiamentoEnum status;

    @OneToMany(mappedBy = "eventoBeneficiamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEventoBeneficiamento> itens = new ArrayList<>();
}
