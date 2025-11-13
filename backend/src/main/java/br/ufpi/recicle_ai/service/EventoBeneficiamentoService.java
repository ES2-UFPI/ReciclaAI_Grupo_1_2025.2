package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
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

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventoBeneficiamentoService {

    private final EventoBeneficiamentoRepository eventoBeneficiamentoRepository;
    private final EventoBeneficiamentoMapper eventoBeneficiamentoMapper;
    private final BeneficiamentoService beneficiamentoService;
    private final ColetorService coletorService;

    @Transactional(readOnly = true)
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
  
    @Transactional(readOnly = true)
    public List<EventoBeneficiamentoDTO> findByBairro(String bairro) {
        List<EventoBeneficiamento> eventos = eventoBeneficiamentoRepository.findByBairro(bairro);
        return eventos.stream()
                .map(eventoBeneficiamentoMapper::toDTO)
                .collect(Collectors.toList());
    }
  
    @Transactional
    public EventoBeneficiamentoDTO update(Long id, EventoBeneficiamentoForm form) {
        return eventoBeneficiamentoRepository.findById(id).map(evento -> {
            // Atualiza o beneficiamento se foi alterado
            if (!evento.getBeneficiamento().getId().equals(form.getBeneficiamentoId())) {
                Beneficiamento beneficiamento = beneficiamentoService.findEntityById(form.getBeneficiamentoId());
                evento.setBeneficiamento(beneficiamento);
            }
            
            // Atualiza o coletor se foi alterado
            if (!evento.getColetor().getId().equals(form.getColetorId())) {
                Coletor coletor = coletorService.findEntityById(form.getColetorId());
                evento.setColetor(coletor);
            }
            
            evento = eventoBeneficiamentoRepository.save(evento);
            return eventoBeneficiamentoMapper.toDTO(evento);
        }).orElseThrow(() -> new RegraDeNegocioException("Evento de Beneficiamento não encontrado!"));
    }
}
