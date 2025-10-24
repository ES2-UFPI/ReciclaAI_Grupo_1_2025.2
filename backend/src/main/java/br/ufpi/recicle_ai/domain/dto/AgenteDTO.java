package br.ufpi.recicle_ai.domain.dto;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import br.ufpi.recicle_ai.domain.model.Capacidade;
import br.ufpi.recicle_ai.domain.model.Cobertura;
import br.ufpi.recicle_ai.domain.model.item.ItemInventario;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AgenteDTO {
    private Long id;
    private String nome;
    private TipoAgenteEnum tipoAgente;
    private String cpf;
    private String cnpj;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ColetorDTO extends AgenteDTO {
        // Atributos futuros podem ser adicionados aqui

        private String tipoColetor;
        private Cobertura cobertura;
        private Capacidade capacidade;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ReceptorDTO extends AgenteDTO {
        // Atributos futuros podem ser adicionados aqui
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ProdutorDTO extends AgenteDTO {
        private List<ItemInventario> itens;

    }

    @Data
    @Builder
    public static class EventoColetaResponseDTO {

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

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ItensDTO {
        private Long id;
        private String nomeItem;
        private String unidadeItem;
        private Double quantidadeEstoque;
    }
}
