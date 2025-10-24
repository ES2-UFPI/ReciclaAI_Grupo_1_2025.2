package br.ufpi.recicle_ai.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import br.ufpi.recicle_ai.domain.model.item.ItemColeta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evento_coleta")
public class EventoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com o Coletor (Muitos Eventos para Um Coletor)
    @ManyToOne 
    @JoinColumn(name = "coletor_id", nullable = false)
    // Usamos JsonIgnoreProperties para evitar loops de serialização com a entidade Coletor
    @JsonIgnoreProperties({"eventosColeta"}) // Se Coletor tiver a lista de EventoColeta, ignore-a
    private Coletor coletor; 

    private LocalDateTime dataInicio;
    
    private LocalDateTime dataFim;

    // Relacionamento com o Ponto de Coleta (Muitos Eventos para Um Ponto)
    @ManyToOne(cascade = CascadeType.ALL) // Se quiser persistir o PontoColeta junto, use ALL
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    @JsonIgnoreProperties({"eventosColeta"}) // Se PontoColeta tiver a lista de EventoColeta
    private PontoColeta pontoColeta;

    // Relacionamento com os Itens a serem Coletados (Um Evento tem Muitos ItensColeta)
    @OneToMany(mappedBy = "eventoColeta", cascade = CascadeType.ALL, orphanRemoval = true)
    // "eventoColeta" deve ser o nome do campo ManyToOne na classe ItemColeta.java
    @JsonIgnoreProperties("eventoColeta") // Evita o loop ItemColeta -> EventoColeta
    private List<ItemColeta> itensColeta;

    // Construtores
    public EventoColeta() {
    }
    
    // Construtor completo (opcional, Lombok pode gerar com @AllArgsConstructor se for seguro)
}
