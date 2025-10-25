package br.ufpi.recicle_ai.domain.model.coleta;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor; // Adicionado para construtor padrão

@Getter
@Setter
@Entity
@Table(name = "tb_ponto_coleta")
@NoArgsConstructor
@AllArgsConstructor
public class PontoColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
}