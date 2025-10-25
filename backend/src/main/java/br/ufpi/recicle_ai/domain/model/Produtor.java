package br.ufpi.recicle_ai.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_produtor")
@NoArgsConstructor
public class Produtor extends Agente {
    
}
