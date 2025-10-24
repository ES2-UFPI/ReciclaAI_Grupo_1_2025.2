package br.ufpi.recicle_ai.controller;

import br.ufpi.recicle_ai.domain.dto.ReceptorDTO;
import br.ufpi.recicle_ai.domain.form.ReceptorForm;
import br.ufpi.recicle_ai.service.ReceptorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/receptores")
public class ReceptorController {

    private final ReceptorService receptorService;

    @Autowired
    public ReceptorController(ReceptorService receptorService) {
        this.receptorService = receptorService;
    }

    @GetMapping
    public ResponseEntity<List<ReceptorDTO>> findAll() {
        return ResponseEntity.ok(receptorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceptorDTO> findById(@PathVariable Long id) {
        ReceptorDTO dto = receptorService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ReceptorDTO> create(@RequestBody @Valid ReceptorForm form) {
        ReceptorDTO dto = receptorService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceptorDTO> update(@PathVariable Long id, @RequestBody @Valid ReceptorForm form) {
        ReceptorDTO dto = receptorService.update(id, form);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        receptorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
