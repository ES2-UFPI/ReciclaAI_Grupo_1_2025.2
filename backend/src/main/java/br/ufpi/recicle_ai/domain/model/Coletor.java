package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Coletor extends Agente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String tipoDeColeta;
    
    // Coletor.java (Modelo)

    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true, optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private Cobertura cobertura;

    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true, optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private Capacidade capacidade;
}
