package br.ufpi.recicle_ai.domain.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor; // Adicionado para construtor padrão

@Getter
@Setter
@Entity
@Table(name = "ponto_coleta")
@NoArgsConstructor // Lombok para construtor padrão (necessário pelo JPA)
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    
    private String numero;
    
    private String bairro; // <-- CAMPO CHAVE PARA O FILTRO
    
    private String cep;
    
    // Construtor completo (opcional)
    public PontoColeta(String logradouro, String numero, String bairro, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
    }
}