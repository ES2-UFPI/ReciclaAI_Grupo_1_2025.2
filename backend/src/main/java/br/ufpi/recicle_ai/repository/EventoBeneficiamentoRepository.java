package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoBeneficiamentoRepository extends JpaRepository<EventoBeneficiamento, Long> {
}
