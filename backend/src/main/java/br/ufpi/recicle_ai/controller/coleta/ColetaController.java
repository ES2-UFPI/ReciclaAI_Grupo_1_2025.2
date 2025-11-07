package br.ufpi.recicle_ai.controller.coleta;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.service.ColetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufpi.recicle_ai.domain.form.coleta.ColetaForm;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/coletas")
@RequiredArgsConstructor
public class ColetaController {
    private final ColetaService coletaService;

    @GetMapping
    public ResponseEntity<Page<ColetaDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(coletaService.findAll(pageable));
    }

    @GetMapping("/por-bairro")
    public ResponseEntity<Page<ColetaDTO>> findByBairro(@RequestParam String bairro, Pageable pageable) {
        return ResponseEntity.ok(coletaService.findByBairro(bairro, pageable));
    }

    @PostMapping
    public ResponseEntity<ColetaDTO> createColetas(@RequestBody @Valid ColetaForm form) {
        ColetaDTO dto = coletaService.createColetas(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
