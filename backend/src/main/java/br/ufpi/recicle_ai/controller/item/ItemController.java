package br.ufpi.recicle_ai.controller.item;

import br.ufpi.recicle_ai.domain.dto.item.ItemDTO;
import br.ufpi.recicle_ai.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarItensComCotacao() {
        return ResponseEntity.ok(itemService.findAll());
    }
}
