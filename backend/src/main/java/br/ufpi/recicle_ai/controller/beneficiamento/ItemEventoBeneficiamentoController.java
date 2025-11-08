package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemEventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemEventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.ItemEventoBeneficiamentoService;
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
@RequestMapping("/itens-evento-beneficiamento")
@RequiredArgsConstructor
public class ItemEventoBeneficiamentoController {

    private final ItemEventoBeneficiamentoService itemEventoBeneficiamentoService;

    @PostMapping
    public ResponseEntity<ItemEventoBeneficiamentoDTO> create(@RequestBody @Valid ItemEventoBeneficiamentoForm form) {
        ItemEventoBeneficiamentoDTO dto = itemEventoBeneficiamentoService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
