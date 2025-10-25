package br.ufpi.recicle_ai.domain.dto.eventoColeta;

import br.ufpi.recicle_ai.domain.dto.ProdutorDTO;
import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoColetaDTO {
    private Long id;
    private ColetaDTO coleta;
    private ProdutorDTO produtor;
    private StatusEventoColetaEnum status;
    private List<ItemEventoColetaDTO> itens = new ArrayList<>();
}
