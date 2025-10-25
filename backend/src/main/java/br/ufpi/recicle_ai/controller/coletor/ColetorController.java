package br.ufpi.recicle_ai.controller.coletor;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.form.ColetorForm;
import br.ufpi.recicle_ai.service.ColetorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/coletores")
public class ColetorController {

    private final ColetorService coletorService;

    @Autowired
    public ColetorController(ColetorService coletorService) {
        this.coletorService = coletorService;
    }

    @GetMapping
    public ResponseEntity<List<ColetorDTO>> findAll() {
        return ResponseEntity.ok(coletorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetorDTO> findById(@PathVariable Long id) {
        ColetorDTO dto = coletorService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ColetorDTO> create(@RequestBody @Valid ColetorForm form) {
        ColetorDTO dto = coletorService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColetorDTO> update(@PathVariable Long id, @RequestBody @Valid ColetorForm form) {
        ColetorDTO dto = coletorService.update(id, form);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coletorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}