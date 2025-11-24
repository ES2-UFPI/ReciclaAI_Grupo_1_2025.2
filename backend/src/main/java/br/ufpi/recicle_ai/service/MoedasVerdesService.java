package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MoedasVerdesService {

    private final ProdutorService produtorService;

    @Transactional
    public void creditarMoedasPorEvento(EventoColeta evento) {
        Produtor produtor = evento.getProdutor();
        BigDecimal totalMoedasGanhas = BigDecimal.ZERO;

        for (ItemEventoColeta item : evento.getItens()) {
            BigDecimal valorMoedasItem = item.getItem().getValorMoedas();
            if (valorMoedasItem == null || valorMoedasItem.compareTo(BigDecimal.ZERO) <= 0) {
                // Pula itens sem valor ou com valor zero/negativo para não causar erros
                continue;
            }
            BigDecimal quantidade = new BigDecimal(item.getQuantidade());
            BigDecimal moedasDoItem = valorMoedasItem.multiply(quantidade);
            totalMoedasGanhas = totalMoedasGanhas.add(moedasDoItem);
        }

        if (totalMoedasGanhas.compareTo(BigDecimal.ZERO) > 0) {
            Produtor produtorParaAtualizar = produtorService.findEntityById(produtor.getId());
            BigDecimal saldoAtual =  produtorParaAtualizar.getSaldoMoedasVerdes() == null ?
                    BigDecimal.ZERO : produtorParaAtualizar.getSaldoMoedasVerdes();
            produtorParaAtualizar.setSaldoMoedasVerdes(saldoAtual.add(totalMoedasGanhas));
            // O save será feito pelo Hibernate ao final da transação do método que chamou este.
        }
    }
}
