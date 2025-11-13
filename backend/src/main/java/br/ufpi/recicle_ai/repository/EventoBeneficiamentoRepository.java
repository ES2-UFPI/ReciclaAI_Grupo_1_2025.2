package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoBeneficiamentoRepository extends JpaRepository<EventoBeneficiamento, Long> {
    boolean existsByBeneficiamentoIdAndColetorId(Long beneficiamentoId, Long coletorId);

    List<EventoBeneficiamento> findAllByColetorId(Long coletorId);

    List<EventoBeneficiamento> findAllByBeneficiamentoReceptorId(Long receptorId);
}
