package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.beneficiamento.ItemBeneficiamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemBeneficiamentoRepository extends JpaRepository<ItemBeneficiamento, Long> {
    Optional<ItemBeneficiamento> findByBeneficiamentoIdAndItemId(Long beneficiamentoId, Long itemId);
}
