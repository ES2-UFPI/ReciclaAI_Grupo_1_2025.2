package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.EventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class EventoBeneficiamentoService {

    private final EventoBeneficiamentoRepository eventoBeneficiamentoRepository;
    private final EventoBeneficiamentoMapper eventoBeneficiamentoMapper;

    public EventoBeneficiamentoDTO findById(Long id){
        return eventoBeneficiamentoMapper.toDTO(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public EventoBeneficiamento findEntityById(Long id) {
        return eventoBeneficiamentoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Evento de Beneficiamento não encontrado!"));
    }

}
