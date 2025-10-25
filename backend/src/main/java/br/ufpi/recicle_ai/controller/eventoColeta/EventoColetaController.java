package br.ufpi.recicle_ai.controller.eventoColeta;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.EventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.EventoColetaForm;
import br.ufpi.recicle_ai.service.EventoColetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eventos-coleta")
@RequiredArgsConstructor
public class EventoColetaController {

    private final EventoColetaService eventoColetaService;

    @PostMapping
    public ResponseEntity<EventoColetaDTO> create(@RequestBody @Valid EventoColetaForm form) {
        EventoColetaDTO dto = eventoColetaService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/produtor/{id}")
    public ResponseEntity<List<EventoColetaDTO>> findAllByProdutorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoColetaService.findAllByProdutorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventoColetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
