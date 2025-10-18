package br.ufpi.recicle_ai.domain.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "capacidades") 
public class Capacidade implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int capacidadeTotal;
    private List<String> tiposResiduos;
    private List<String> equipamentos;

    public Capacidade() {
    }

    public Capacidade(int capacidadeTotal, List<String> tiposResiduos, List<String> equipamentos) {
        this.capacidadeTotal = capacidadeTotal;
        this.tiposResiduos = tiposResiduos;
        this.equipamentos = equipamentos;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public int getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(int capacidadeTotal) {
        this.capacidadeTotal = capacidadeTotal;
    }

    public List<String> getTiposResiduos() {
        return tiposResiduos;
    }

    public void setTiposResiduos(List<String> tiposResiduos) {
        this.tiposResiduos = tiposResiduos;
    }

    public List<String> getEquipamentos() {
        return equipamentos;
    }
    
    public void setEquipamentos(List<String> equipamentos) {
        this.equipamentos = equipamentos;
    }

}
