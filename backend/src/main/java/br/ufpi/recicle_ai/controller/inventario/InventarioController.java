package br.ufpi.recicle_ai.controller.inventario;

import br.ufpi.recicle_ai.domain.dto.item.ItemInventarioDTO;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.form.item.ItemInventarioUpdateForm;
import br.ufpi.recicle_ai.service.ItemInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ItemInventarioService itemInventarioService;

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<ItemInventarioDTO>> listarInventarioPorPessoa(
            @PathVariable Long pessoaId,
            @RequestParam TipoPessoaEnum tipoPessoa) {
        List<ItemInventarioDTO> itens = itemInventarioService.listarItensPorPessoa(pessoaId, tipoPessoa);
        return ResponseEntity.ok(itens);
    }

    @PutMapping("/{itemInventarioId}")
    public ResponseEntity<ItemInventarioDTO> atualizarQuantidade(
            @PathVariable Long itemInventarioId,
            @RequestBody @Valid ItemInventarioUpdateForm form) {
        ItemInventarioDTO itemAtualizado = itemInventarioService.atualizarQuantidade(itemInventarioId, form);
        return ResponseEntity.ok(itemAtualizado);
    }
}
