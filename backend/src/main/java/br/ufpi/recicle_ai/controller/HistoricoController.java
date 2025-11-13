package br.ufpi.recicle_ai.controller;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.dto.HistoricoDTO;
import br.ufpi.recicle_ai.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoService historicoService;

    @GetMapping
    public ResponseEntity<List<HistoricoDTO>> buscarHistorico(
            @RequestParam Long pessoaId,
            @RequestParam TipoPessoaEnum tipoPessoa) {
        return ResponseEntity.ok(historicoService.buscarHistorico(pessoaId, tipoPessoa));
    }
}