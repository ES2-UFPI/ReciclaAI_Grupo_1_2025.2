package br.ufpi.recicle_ai.controller.eventoColeta;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.ItemEventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.ItemEventoColetaForm;
import br.ufpi.recicle_ai.service.ItemEventoColetaService;
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
@RequestMapping("/itens-evento-coleta")
@RequiredArgsConstructor
public class ItemEventoColetaController {

    private final ItemEventoColetaService itemEventoColetaService;

    @PostMapping
    public ResponseEntity<ItemEventoColetaDTO> create(@RequestBody @Valid ItemEventoColetaForm form) {
        ItemEventoColetaDTO dto = itemEventoColetaService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
