package br.ufpi.recicle_ai.service;

import org.springframework.transaction.annotation.Transactional;

import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.mapper.PontoColetaMapper;
import br.ufpi.recicle_ai.repository.PontoColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PontoColetaService {

    private final PontoColetaRepository pontoColetaRepository;
    
    @Transactional(readOnly = true)
    public PontoColeta findEntityById(Long id) {
        return pontoColetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coletor não encontrado!"));
    }

    @Transactional
public PontoColeta create(PontoColeta ponto) {
    // Tenta encontrar por logradouro + número + bairro
    return pontoColetaRepository.save(ponto);
}


}
