package br.ufpi.recicle_ai.controller.coleta;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.service.ColetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coletas")
@RequiredArgsConstructor
public class ColetaController {
    private final ColetaService coletaService;

    @GetMapping
    public ResponseEntity<Page<ColetaDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(coletaService.findAll(pageable));
    }

}
