package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tb_produtor")
@NoArgsConstructor
public class Produtor extends Agente {

    @Column(name = "saldo_moedas_verdes", precision = 19, scale = 2)
    private BigDecimal saldoMoedasVerdes = BigDecimal.ZERO;
}
