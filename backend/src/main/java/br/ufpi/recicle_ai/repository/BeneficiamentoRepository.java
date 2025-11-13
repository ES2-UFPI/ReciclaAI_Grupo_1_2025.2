package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiamentoRepository extends JpaRepository<Beneficiamento, Long> {
    Page<Beneficiamento> findAllByReceptorIdOrderByDataInicioAsc(Long receptorId, Pageable pageable);
}
