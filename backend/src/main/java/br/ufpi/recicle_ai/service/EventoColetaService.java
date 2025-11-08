package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.EventoColetaDTO;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.form.eventoColeta.EventoColetaForm;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.EventoColetaMapper;
import br.ufpi.recicle_ai.repository.EventoColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoColetaService {

    private final EventoColetaRepository eventoColetaRepository;
    private final ColetaService coletaService;
    private final ProdutorService produtorService;
    private final ItemInventarioService itemInventarioService;
    private final EventoColetaMapper eventoColetaMapper;

    @Transactional
    public EventoColetaDTO create(EventoColetaForm form) {
        if (eventoColetaRepository.existsByColetaIdAndProdutorId(form.getColetaId(), form.getProdutorId())) {
            throw new RegraDeNegocioException("Este produtor já agendou participação para esta coleta.");
        }

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
                .orElseThrow(() -> new RegraDeNegocioException("Evento de Coleta não encontrado!"));
    }

    @Transactional(readOnly = true)
    public List<EventoColetaDTO> findAllByProdutorId(Long produtorId) {
        return eventoColetaRepository.findAllByProdutorId(produtorId).stream()
                .map(eventoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventoColetaDTO> findAllByColetorId(Long coletorId) {
        return eventoColetaRepository.findAllByColetaColetorId(coletorId).stream()
                .map(eventoColetaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        EventoColeta eventoColeta = findEntityById(id);

        if (eventoColeta.getStatus() == StatusEventoColetaEnum.CONCLUIDA) {
            throw new RegraDeNegocioException("Não é possível cancelar um evento de coleta que já foi concluído.");
        }

        Long produtorId = eventoColeta.getProdutor().getId();
        for (ItemEventoColeta item : eventoColeta.getItens()) {
            BigDecimal quantidadeARestituir = new BigDecimal(item.getQuantidade());
            itemInventarioService.creditarNoInventario(produtorId, TipoPessoaEnum.PRODUTOR, item.getItem().getId(), quantidadeARestituir);
        }

        eventoColetaRepository.delete(eventoColeta);
    }

    @Transactional
    public EventoColetaDTO confirmarEvento(Long id) {
        EventoColeta eventoColeta = findEntityById(id);

        if (eventoColeta.getStatus() == StatusEventoColetaEnum.CONCLUIDA) {
            throw new RegraDeNegocioException("Este evento de coleta já está concluído.");
        }

        // Adiciona os itens ao inventário do coletor
        Long coletorId = eventoColeta.getColeta().getColetor().getId();
        for (ItemEventoColeta item : eventoColeta.getItens()) {
            BigDecimal quantidadeACreditar = new BigDecimal(item.getQuantidade());
            itemInventarioService.creditarNoInventario(coletorId, TipoPessoaEnum.COLETOR, item.getItem().getId(), quantidadeACreditar);
        }

        // Atualiza o status do evento
        eventoColeta.setStatus(StatusEventoColetaEnum.CONCLUIDA);
        eventoColetaRepository.save(eventoColeta);
        return eventoColetaMapper.toDTO(eventoColeta);
    }


}
