package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.dto.HistoricoDTO;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import br.ufpi.recicle_ai.repository.EventoColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final EventoColetaRepository eventoColetaRepository;
    private final EventoBeneficiamentoRepository eventoBeneficiamentoRepository;

    @Transactional(readOnly = true)
    public List<HistoricoDTO> buscarHistorico(Long pessoaId, TipoPessoaEnum tipoPessoa) {
        List<HistoricoDTO> historicoColetas = new ArrayList<>();
        List<HistoricoDTO> historicoBeneficiamentos = new ArrayList<>();

        switch (tipoPessoa) {
            case PRODUTOR:
                historicoColetas = eventoColetaRepository.findAllByProdutorIdAndStatus(pessoaId, StatusEventoColetaEnum.CONCLUIDA)
                        .stream().map(this::mapEventoColetaToHistoricoDTO).collect(Collectors.toList());
                break;

            case RECEPTOR:
                historicoBeneficiamentos = eventoBeneficiamentoRepository.findAllByBeneficiamentoReceptorIdAndStatus(pessoaId, StatusBeneficiamentoEnum.CONCLUIDA)
                        .stream().map(this::mapEventoBeneficiamentoToHistoricoDTO).collect(Collectors.toList());
                break;

            case COLETOR:
                historicoColetas = eventoColetaRepository.findAllByColetaColetorIdAndStatus(pessoaId, StatusEventoColetaEnum.CONCLUIDA)
                        .stream().map(this::mapEventoColetaToHistoricoDTO).collect(Collectors.toList());
                historicoBeneficiamentos = eventoBeneficiamentoRepository.findAllByColetorIdAndStatus(pessoaId, StatusBeneficiamentoEnum.CONCLUIDA)
                        .stream().map(this::mapEventoBeneficiamentoToHistoricoDTO).collect(Collectors.toList());
                break;
        }

        // Combina as duas listas e ordena pela data do evento, da mais recente para a mais antiga
        return Stream.concat(historicoColetas.stream(), historicoBeneficiamentos.stream())
                .sorted(Comparator.comparing(HistoricoDTO::getData).reversed())
                .collect(Collectors.toList());
    }

    private HistoricoDTO mapEventoColetaToHistoricoDTO(EventoColeta evento) {
        return HistoricoDTO.builder()
                .eventoId(evento.getId())
                .tipoEvento("COLETA")
                .status(evento.getStatus().toString())
                .data(evento.getColeta().getDataInicio())
                .nomeParticipante(evento.getProdutor().getNome()) // O outro participante é o Produtor
                .nomeLocal(evento.getColeta().getPontoColeta().getLogradouro())
                .itens(evento.getItens().stream()
                        .map(item -> String.format("%s: %d %s", item.getItem().getNome(), item.getQuantidade(), item.getItem().getUnidade()))
                        .collect(Collectors.toList()))
                .build();
    }

    private HistoricoDTO mapEventoBeneficiamentoToHistoricoDTO(EventoBeneficiamento evento) {
        return HistoricoDTO.builder()
                .eventoId(evento.getId())
                .tipoEvento("BENEFICIAMENTO")
                .status(evento.getStatus().toString())
                .data(evento.getBeneficiamento().getDataInicio())
                .nomeParticipante(evento.getColetor().getNome()) // O outro participante é o Coletor
                .nomeLocal(evento.getBeneficiamento().getPontoColeta().getLogradouro())
                .itens(evento.getItens().stream()
                        .map(item -> String.format("%s: %d %s", item.getItem().getNome(), item.getQuantidade(), item.getItem().getUnidade()))
                        .collect(Collectors.toList()))
                .build();
    }
}
