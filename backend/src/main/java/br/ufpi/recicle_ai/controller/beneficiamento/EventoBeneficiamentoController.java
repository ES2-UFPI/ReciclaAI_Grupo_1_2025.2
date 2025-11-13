package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.EventoBeneficiamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eventos-beneficiamento")
@RequiredArgsConstructor
@Tag(name = "Eventos de Beneficiamento", description = "Endpoints para gerenciamento de eventos de beneficiamento")
public class EventoBeneficiamentoController {

    private final EventoBeneficiamentoService eventoBeneficiamentoService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID", description = "Retorna um evento de beneficiamento específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<EventoBeneficiamentoDTO> visualizarEvento(@PathVariable Long id) {
        EventoBeneficiamentoDTO dto = eventoBeneficiamentoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EventoBeneficiamentoDTO> create(@RequestBody @Valid EventoBeneficiamentoForm form) {
        EventoBeneficiamentoDTO dto = eventoBeneficiamentoService.create(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/coletor/{id}")
    @Operation(summary = "Listar eventos por Coletor", description = "Retorna todos os eventos de beneficiamento de um coletor específico")
    public ResponseEntity<List<EventoBeneficiamentoDTO>> findAllByColetorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoBeneficiamentoService.findAllByColetorId(id));
    }

    @GetMapping("/receptor/{id}")
    @Operation(summary = "Listar eventos por Receptor", description = "Retorna todos os eventos de beneficiamento de um receptor específico")
    public ResponseEntity<List<EventoBeneficiamentoDTO>> findAllByReceptorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoBeneficiamentoService.findAllByReceptorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento de beneficiamento", description = "Deleta um evento de beneficiamento pelo ID e restitui os itens ao coletor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Não é possível deletar evento concluído", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventoBeneficiamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
