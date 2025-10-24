package br.ufpi.recicle_ai.domain.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventoColetaResponseDTO {
    
    private Long id;
    private ColetorDTO coletor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private PontoColetaDTO pontoColeta;
    private List<ItemColetaDTO> itensColeta;

    // --- DTOs Aninhados ---

    @Data
    @Builder
    public static class ColetorDTO {
        private Long id;
        private String nome;
        private String cpf;
        private String tipo; // TipoAgenteEnum
    }
    
    @Data
    @Builder
    public static class PontoColetaDTO {
        private Long id;
        private String logradouro;
        private String numero;
        private String bairro;
        private String cep;
    }
    
    @Data
    @Builder
    public static class ItemColetaDTO {
        private Long id;
        private Long itemId;
        private String nomeItem;
        private String unidadeItem;
        private Integer qtdMinima;
    }
}
