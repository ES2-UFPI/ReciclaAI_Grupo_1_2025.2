package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemBeneficiamentoForm;
import br.ufpi.recicle_ai.service.ItemBeneficiamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/itens-beneficiamento")
@RequiredArgsConstructor
public class ItemBeneficiamentoController {

    private final ItemBeneficiamentoService itemBeneficiamentoService;

    @PostMapping
    public ResponseEntity<ItemBeneficiamentoDTO> create(@RequestBody @Valid ItemBeneficiamentoForm form) {
        ItemBeneficiamentoDTO dto = itemBeneficiamentoService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
