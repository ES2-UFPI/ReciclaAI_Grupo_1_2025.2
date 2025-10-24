package br.ufpi.recicle_ai.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventoColetaResponseDTO {
    private Long id;
    private ColetorDTO coletor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private PontoColetaDTO pontoColeta;
    private List<ItemColetaDTO> itensColeta;
}
