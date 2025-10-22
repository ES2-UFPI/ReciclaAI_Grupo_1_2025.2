package br.ufpi.recicle_ai.domain.model.dto.form;

import java.util.List;

import br.ufpi.recicle_ai.domain.enuns.TipoAgenteEnum;
import br.ufpi.recicle_ai.domain.form.AgenteForm;
import br.ufpi.recicle_ai.domain.model.Itens;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProdutorForm extends AgenteForm {
    
    //private List<Itens> itens;
}
