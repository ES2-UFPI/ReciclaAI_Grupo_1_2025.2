package br.ufpi.recicle_ai.controller;

import br.ufpi.recicle_ai.domain.model.dto.ProdutorDTO;
import br.ufpi.recicle_ai.domain.model.dto.form.ItensForm;
import br.ufpi.recicle_ai.domain.model.dto.form.ProdutorForm;
import br.ufpi.recicle_ai.domain.service.ItensService;
import br.ufpi.recicle_ai.service.ProdutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtores")
@RequiredArgsConstructor
public class ProdutorController {

    private final ItensService itensService;

    @Autowired
    private final ProdutorService produtorService;

    @GetMapping
    public ResponseEntity<List<ProdutorDTO>> findAll() {
        return ResponseEntity.ok(produtorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutorDTO> findById(@PathVariable Long id) {
        ProdutorDTO dto = produtorService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProdutorDTO> create(@RequestBody @Valid ProdutorForm form) {
        ProdutorDTO dto = produtorService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutorDTO> update(@PathVariable Long id, @RequestBody @Valid ProdutorForm form) {
        ProdutorDTO dto = produtorService.update(id, form);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-item")
    public ResponseEntity<Void> addItem(@PathVariable Long id, @RequestBody @Valid ItensForm form) {
        itensService.addItensProdutor(id, form);
        return ResponseEntity.noContent().build();
    }
}
