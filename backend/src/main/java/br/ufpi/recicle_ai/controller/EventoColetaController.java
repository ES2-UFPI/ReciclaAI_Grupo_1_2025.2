package br.ufpi.recicle_ai.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpi.recicle_ai.domain.model.dto.EventoColetaResponseDTO;
import br.ufpi.recicle_ai.domain.service.EventoColetaService;

@RestController
@RequestMapping("/eventos-coleta")
public class EventoColetaController {

    private final EventoColetaService service;

    
    public EventoColetaController(EventoColetaService service) {
        this.service = service;
    }

    // Endpoint: GET /eventos-coleta?bairro=NomeDoBairro
    @GetMapping
    public ResponseEntity<List<EventoColetaResponseDTO>> buscarPorBairro(
        @RequestParam(name = "bairro", required = true) String bairro) 
    {
        List<EventoColetaResponseDTO> eventos = service.buscarEventosPorBairro(bairro);
        
        // Retorna 200 OK com a lista (pode ser vazia, se não houver resultados)
        return ResponseEntity.ok(eventos);
    }
}