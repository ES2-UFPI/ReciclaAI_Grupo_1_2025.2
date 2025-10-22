package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pontos_coleta")
public class PontoColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "endereco", nullable = false)
    private String endereco;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "descricao")
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "coletor_id")
    private Coletor coletor;
}