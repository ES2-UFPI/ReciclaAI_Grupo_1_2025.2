package br.ufpi.recicle_ai.controller.coleta;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.service.ColetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
