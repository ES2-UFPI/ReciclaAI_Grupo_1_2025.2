package br.ufpi.recicle_ai.domain.dto.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import lombok.Data;

import java.util.List;

@Data
public class EventoBeneficiamentoDTO {
    private Long id;
    private BeneficiamentoDTO beneficiamento;
    private ColetorDTO coletor;
    private StatusBeneficiamentoEnum status;
    private List<ItemEventoBeneficiamentoDTO> itens;
}
