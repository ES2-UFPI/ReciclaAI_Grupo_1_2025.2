package br.ufpi.recicle_ai.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoDTO {

    private Long eventoId;
    private String tipoEvento; // "COLETA" ou "BENEFICIAMENTO"
    private String status;
    private LocalDateTime data;
    private String nomeParticipante; // Nome do Produtor (para Coletor) ou Coletor (para Receptor/Produtor)
    private String nomeLocal; // Nome do Ponto de Coleta
    private List<String> itens;

}