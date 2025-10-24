package br.ufpi.recicle_ai.controller;

import java.util.List;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpi.recicle_ai.service.EventoColetaService;

@RestController
@RequestMapping("/eventos-coleta")
@RequiredArgsConstructor
public class EventoColetaController {

    private final EventoColetaService service;

//    @GetMapping
//    public ResponseEntity<List<EventoColetaDTO>> buscarPorBairro(
//        @RequestParam(name = "bairro", required = true) String bairro)
//    {
////        List<AgenteDTO.EventoColetaResponseDTO> eventos = service.buscarEventosPorBairro(bairro);
////
////        // Retorna 200 OK com a lista (pode ser vazia, se não houver resultados)
////        return ResponseEntity.ok(eventos);
//    }
}