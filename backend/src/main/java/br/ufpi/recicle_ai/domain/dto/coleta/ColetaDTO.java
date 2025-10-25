package br.ufpi.recicle_ai.domain.dto.coleta;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColetaDTO {
    private Long id;
    private ColetorDTO coletor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private PontoColetaDTO pontoColeta;
    private List<ItemColetaDTO> itensColeta;
}
