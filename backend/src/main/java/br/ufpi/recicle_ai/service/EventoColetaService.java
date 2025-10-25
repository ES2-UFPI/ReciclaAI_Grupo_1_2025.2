package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.EventoColetaDTO;
import br.ufpi.recicle_ai.domain.form.eventoColeta.EventoColetaForm;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import br.ufpi.recicle_ai.mapper.EventoColetaMapper;
import br.ufpi.recicle_ai.repository.EventoColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoColetaService {

    private final EventoColetaRepository eventoColetaRepository;
    private final ColetaService coletaService;
    private final ProdutorService produtorService;
    private final EventoColetaMapper eventoColetaMapper;

    @Transactional
    public EventoColetaDTO create(EventoColetaForm form) {
        Coleta coleta = coletaService.findEntityById(form.getColetaId());
        Produtor produtor = produtorService.findEntityById(form.getProdutorId());

        EventoColeta eventoColeta = eventoColetaMapper.toModel(form);
        eventoColeta.setColeta(coleta);
        eventoColeta.setProdutor(produtor);
        eventoColeta.setStatus(StatusEventoColetaEnum.AGENDADA);

        eventoColeta = eventoColetaRepository.save(eventoColeta);
        return eventoColetaMapper.toDTO(eventoColeta);
    }

    @Transactional(readOnly = true)
    public EventoColeta findEntityById(Long id) {
        return eventoColetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento de Coleta não encontrado!"));
    }

    @Transactional(readOnly = true)
    public List<EventoColetaDTO> findAllByProdutorId(Long produtorId) {
        return eventoColetaRepository.findAllByProdutorId(produtorId).stream()
                .map(eventoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
