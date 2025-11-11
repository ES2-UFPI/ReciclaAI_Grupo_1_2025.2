package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.service.BeneficiamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/beneficiamentos")
@RequiredArgsConstructor
public class BeneficiamentoController {

    private final BeneficiamentoService service;

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/receptor/{id}")
    public ResponseEntity<List<BeneficiamentoDTO>> findByReceptorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findEntityByReceptorId(id));
    }
}
