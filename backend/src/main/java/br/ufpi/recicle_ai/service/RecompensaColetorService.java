package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemBeneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemEventoBeneficiamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecompensaColetorService {

    private final ColetorService coletorService;

    @Transactional
    public void creditarRecompensaPorEvento(EventoBeneficiamento evento) {
        Coletor coletor = evento.getColetor();
        BigDecimal totalRecompensa = BigDecimal.ZERO;
        List<ItemBeneficiamento> itensBeneficiamento = evento.getBeneficiamento().getItensBeneficiamento();

        for (ItemEventoBeneficiamento item : evento.getItens()) {
            Optional<ItemBeneficiamento> optValorItem = itensBeneficiamento.stream()
                    .filter(i -> i.getItem().equals(item.getItem()))
                    .findFirst();

            if (optValorItem.isEmpty()) {
                continue;
            }

            BigDecimal valorItem = optValorItem.get().getValor();
            BigDecimal quantidade = new BigDecimal(item.getQuantidade());
            BigDecimal recompensaDoItem = valorItem.multiply(quantidade);
            totalRecompensa = totalRecompensa.add(recompensaDoItem);
        }

        if (totalRecompensa.compareTo(BigDecimal.ZERO) > 0) {
            Coletor coletorParaAtualizar = coletorService.findEntityById(coletor.getId());
            BigDecimal saldoAtual = coletorParaAtualizar.getSaldo() != null ? coletorParaAtualizar.getSaldo() : BigDecimal.ZERO;
            coletorParaAtualizar.setSaldo(saldoAtual.add(totalRecompensa));
        }
    }
}
