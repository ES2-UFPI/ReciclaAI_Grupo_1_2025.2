package br.ufpi.recicle_ai.domain.dto.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoBeneficiamentoDTO {
    private Long id;
    private BeneficiamentoDTO beneficiamento;
    private ColetorDTO coletor;
    private StatusBeneficiamentoEnum status = StatusBeneficiamentoEnum.AGENDADA;
    private List<ItemEventoBeneficiamentoDTO> itens = new ArrayList<>();
}
