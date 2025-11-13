package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.MensagemDTO;
import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.EventoBeneficiamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @GetMapping("/bairro/{bairro}")
    @Operation(summary = "Listar eventos por bairro", description = "Retorna todos os eventos de beneficiamento de um bairro específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso",
                content = @Content(schema = @Schema(implementation = EventoBeneficiamentoDTO.class))),
        @ApiResponse(responseCode = "200", description = "Nenhum evento encontrado para o bairro informado",
                content = @Content(schema = @Schema(implementation = MensagemDTO.class)))
    })
    public ResponseEntity<?> listarEventosPorBairro(
            @Parameter(description = "Nome do bairro", example = "Centro")
            @PathVariable String bairro) {
        List<EventoBeneficiamentoDTO> eventos = eventoBeneficiamentoService.findByBairro(bairro);
        
        if (eventos.isEmpty()) {
            MensagemDTO mensagem = new MensagemDTO("Não existem eventos de beneficiamento no bairro: " + bairro);
            return ResponseEntity.ok(mensagem);
        }
        
        return ResponseEntity.ok(eventos);
    }
}
