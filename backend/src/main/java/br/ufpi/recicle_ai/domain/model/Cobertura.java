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
@Table(name = "coberturas")
public class Cobertura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int geofence;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    private List<String> horarios;

    public Cobertura() {
    }

    public Cobertura(int geofence, String rua, String cidade, String estado, String cep, List<String> horarios) {
        this.geofence = geofence;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.horarios = horarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    // --- OUTROS MÉTODOS (setGeofence corrigido) ---
    public int getGeofence() {
        return geofence;
    }

    // Corrigido para receber apenas 'geofence'
    public void setGeofence(int geofence) { 
        this.geofence = geofence;
    }

    // ... (restante dos métodos getRua, setRua, etc., que estão corretos) ...
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public void adicionarHorario(String horario) {
        // É importante verificar se a lista não é nula antes de adicionar
        if (this.horarios != null) { 
             this.horarios.add(horario);
        }
    }
    
    public void removerHorario(String horario) {
        if (this.horarios != null) {
            this.horarios.remove(horario);
        }
    }
}