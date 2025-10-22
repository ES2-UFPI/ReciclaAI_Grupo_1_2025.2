package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eventos_coleta")
public class EventoColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;
    
    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @ManyToOne
    @JoinColumn(name = "coletor_id", nullable = false)
    private Coletor coletor;
    
    @ManyToOne
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    private PontoColeta pontoColeta;
    
    @OneToMany(mappedBy = "eventoColeta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemColeta> itensColeta;
}