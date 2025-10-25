package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.mapper.ColetaMapper;
import br.ufpi.recicle_ai.repository.ColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColetaService {

    private final ColetaRepository coletaRepository;
    private final ColetaMapper coletaMapper;

    @Transactional(readOnly = true)
    public Page<ColetaDTO> findAll(Pageable pageable) {
        return coletaRepository.findAll(pageable).map(coletaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Coleta findEntityById(Long id) {
        return coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada!"));
    }
}
