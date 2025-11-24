package br.ufpi.recicle_ai.domain.model;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tb_coletor")
@NoArgsConstructor
public class Coletor extends Agente {
    private BigDecimal saldo;
}
