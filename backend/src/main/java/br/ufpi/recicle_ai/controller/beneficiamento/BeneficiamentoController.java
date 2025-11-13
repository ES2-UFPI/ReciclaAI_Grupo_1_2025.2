package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.dto.beneficiamento.ItemBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.ItemBeneficiamentoForm;
import br.ufpi.recicle_ai.service.BeneficiamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.ufpi.recicle_ai.domain.form.beneficiamento.BeneficiamentoForm;

import java.util.List;

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
    public ResponseEntity<List<BeneficiamentoDTO>> findByReceptorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findEntityByReceptorId(id));
    }
}
