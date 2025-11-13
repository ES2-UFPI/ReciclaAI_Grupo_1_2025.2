package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemEventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemEventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.EventoBeneficiamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/eventos-beneficiamento")
@RequiredArgsConstructor
public class EventoBeneficiamentoController {

    private final EventoBeneficiamentoService eventoBeneficiamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<EventoBeneficiamentoDTO> visualizarEvento(@PathVariable Long id) {
        EventoBeneficiamentoDTO dto = eventoBeneficiamentoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    ResponseEntity<EventoBeneficiamentoDTO> create(@RequestBody @Valid EventoBeneficiamentoForm form) {
        EventoBeneficiamentoDTO dto = eventoBeneficiamentoService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
