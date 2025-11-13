package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.EventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.BeneficiamentoService;
import br.ufpi.recicle_ai.service.ColetorService;

@Service
@RequiredArgsConstructor
public class EventoBeneficiamentoService {

    private final EventoBeneficiamentoRepository eventoBeneficiamentoRepository;
    private final EventoBeneficiamentoMapper eventoBeneficiamentoMapper;
    private final BeneficiamentoService beneficiamentoService;
    private final ColetorService coletorService;

    public EventoBeneficiamentoDTO findById(Long id){
        return eventoBeneficiamentoMapper.toDTO(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public EventoBeneficiamento findEntityById(Long id) {
        return eventoBeneficiamentoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Evento de Beneficiamento não encontrado!"));
    }

    @Transactional
    public EventoBeneficiamentoDTO create(EventoBeneficiamentoForm form) {
        EventoBeneficiamento eventoBeneficiamento = eventoBeneficiamentoMapper.toModel(form);
        Beneficiamento beneficiamento = beneficiamentoService.findEntityById(form.getBeneficiamentoId());
        eventoBeneficiamento.setBeneficiamento(beneficiamento);
        Coletor coletor = coletorService.findEntityById(form.getColetorId());
        eventoBeneficiamento.setColetor(coletor);
        eventoBeneficiamento = eventoBeneficiamentoRepository.save(eventoBeneficiamento);
        return eventoBeneficiamentoMapper.toDTO(eventoBeneficiamento);
    }

}
