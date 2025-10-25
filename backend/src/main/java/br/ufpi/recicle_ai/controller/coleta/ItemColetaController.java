package br.ufpi.recicle_ai.controller.coleta;

import br.ufpi.recicle_ai.domain.dto.coleta.ItemColetaDTO;
import br.ufpi.recicle_ai.domain.form.coleta.ItemColetaForm;
import br.ufpi.recicle_ai.service.ItemColetaService;
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
@RequestMapping("/itens-coleta")
@RequiredArgsConstructor
public class ItemColetaController {

    private final ItemColetaService itemColetaService;

    @PostMapping
    public ResponseEntity<ItemColetaDTO> create(@RequestBody @Valid ItemColetaForm form) {
        ItemColetaDTO dto = itemColetaService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
