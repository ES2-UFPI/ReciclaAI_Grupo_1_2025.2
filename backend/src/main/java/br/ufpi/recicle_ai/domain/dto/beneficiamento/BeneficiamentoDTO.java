package br.ufpi.recicle_ai.domain.dto.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.coleta.PontoColetaDTO;
import br.ufpi.recicle_ai.domain.dto.ReceptorDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BeneficiamentoDTO {
    private Long id;
    private ReceptorDTO receptor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private PontoColetaDTO pontoColeta;
    private List<ItemBeneficiamentoDTO> itensBeneficiamento;
}
