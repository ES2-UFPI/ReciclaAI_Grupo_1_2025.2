package br.ufpi.recicle_ai.controller;

import br.ufpi.recicle_ai.domain.model.dto.ItensDTO;
import br.ufpi.recicle_ai.domain.model.dto.ProdutorDTO;
import br.ufpi.recicle_ai.domain.model.dto.form.ItensForm;
import br.ufpi.recicle_ai.domain.model.dto.form.ProdutorForm;
import br.ufpi.recicle_ai.domain.service.ItensService;
import br.ufpi.recicle_ai.service.ProdutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    @PostMapping("/{id}/adicionar-item")
    public ResponseEntity<ItensDTO> addItem(@PathVariable Long id, @RequestBody @Valid ItensForm form) throws NotFoundException {
        ItensDTO dto;
        boolean isUpdate = produtorService.findItemByIdAndProdutorId(form.getNomeItem(), id);
        
        if (isUpdate) {
            // 1. Caso de Atualização (Item já existe no inventário)
            dto = itensService.updateItensProdutor(id, form);
            // Retorna 200 OK
            return ResponseEntity.ok(dto); 
        } else {
            // 2. Caso de Criação (Novo item no inventário)
            dto = itensService.addItensProdutor(id, form);
            // Retorna 201 Created
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{itemId}")
                    .buildAndExpand(dto.getId()).toUri();
            return ResponseEntity.created(uri).body(dto);
        }
    }

    @GetMapping("/inventario/{id}")
    public ResponseEntity<List<ItensDTO>> listarInventario(@PathVariable Long id) {
        List<ItensDTO> itens = itensService.listarItensPorProdutor(id);

        // Retorna 200 OK com a lista (mesmo se estiver vazia)
        return ResponseEntity.ok(itens);
    }

}
