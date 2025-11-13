package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.BeneficiamentoForm;
import br.ufpi.recicle_ai.service.BeneficiamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/beneficiamentos")
@RequiredArgsConstructor
public class BeneficiamentoController {

    private final BeneficiamentoService service;

    @PostMapping
    public ResponseEntity<BeneficiamentoDTO> create(@RequestBody @Valid BeneficiamentoForm form) {
        BeneficiamentoDTO dto = service.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/receptor/{id}")
    public ResponseEntity<Page<BeneficiamentoDTO>> findByReceptor(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(service.findByReceptor(id, pageable));
    }
}
