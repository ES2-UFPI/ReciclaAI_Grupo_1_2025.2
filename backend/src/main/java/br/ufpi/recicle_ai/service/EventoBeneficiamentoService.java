package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemEventoBeneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.EventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoBeneficiamentoService {

    private final EventoBeneficiamentoRepository eventoBeneficiamentoRepository;
    private final EventoBeneficiamentoMapper eventoBeneficiamentoMapper;
    private final BeneficiamentoService beneficiamentoService;
    private final ColetorService coletorService;
    private final ItemInventarioService itemInventarioService;

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
        if (eventoBeneficiamentoRepository.existsByBeneficiamentoIdAndColetorId(form.getBeneficiamentoId(), form.getColetorId())) {
            throw new RegraDeNegocioException("Este coletor já agendou participação para este beneficiamento.");
        }
        
        EventoBeneficiamento eventoBeneficiamento = eventoBeneficiamentoMapper.toModel(form);
        Beneficiamento beneficiamento = beneficiamentoService.findEntityById(form.getBeneficiamentoId());
        eventoBeneficiamento.setBeneficiamento(beneficiamento);
        Coletor coletor = coletorService.findEntityById(form.getColetorId());
        eventoBeneficiamento.setColetor(coletor);
        eventoBeneficiamento.setStatus(StatusBeneficiamentoEnum.AGENDADA);
        
        eventoBeneficiamento = eventoBeneficiamentoRepository.save(eventoBeneficiamento);
        return eventoBeneficiamentoMapper.toDTO(eventoBeneficiamento);
    }

    @Transactional
    public void delete(Long id) {
        EventoBeneficiamento evento = findEntityById(id);

        if (evento.getStatus() == StatusBeneficiamentoEnum.CONCLUIDA) {
            throw new RegraDeNegocioException("Não é possível cancelar um evento de beneficiamento que já foi concluído.");
        }

        Long coletorId = evento.getColetor().getId();
        for (ItemEventoBeneficiamento item : evento.getItens()) {
            BigDecimal quantidadeARestituir = new BigDecimal(item.getQuantidade());
            itemInventarioService.creditarNoInventario(coletorId, TipoPessoaEnum.COLETOR, item.getItem().getId(), quantidadeARestituir);
        }

        eventoBeneficiamentoRepository.delete(evento);
    }

    @Transactional
    public EventoBeneficiamentoDTO confirmarEvento(Long id) {
        EventoBeneficiamento evento = findEntityById(id);

        if (evento.getStatus() == StatusBeneficiamentoEnum.CONCLUIDA) {
            throw new RegraDeNegocioException("Este evento de beneficiamento já está concluído.");
        }

        Long receptorId = evento.getBeneficiamento().getReceptor().getId();
        for (ItemEventoBeneficiamento item : evento.getItens()) {
            BigDecimal quantidadeACreditar = new BigDecimal(item.getQuantidade());
            itemInventarioService.creditarNoInventario(receptorId, TipoPessoaEnum.RECEPTOR, item.getItem().getId(), quantidadeACreditar);
        }

        evento.setStatus(StatusBeneficiamentoEnum.CONCLUIDA);
        EventoBeneficiamento eventoSalvo = eventoBeneficiamentoRepository.save(evento);
        return eventoBeneficiamentoMapper.toDTO(eventoSalvo);
    }

    @Transactional(readOnly = true)
    public List<EventoBeneficiamentoDTO> findAllByColetorId(Long coletorId) {
        return eventoBeneficiamentoRepository.findAllByColetorId(coletorId).stream()
                .map(eventoBeneficiamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventoBeneficiamentoDTO> findAllByReceptorId(Long receptorId) {
        return eventoBeneficiamentoRepository.findAllByBeneficiamentoReceptorId(receptorId).stream()
                .map(eventoBeneficiamentoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
