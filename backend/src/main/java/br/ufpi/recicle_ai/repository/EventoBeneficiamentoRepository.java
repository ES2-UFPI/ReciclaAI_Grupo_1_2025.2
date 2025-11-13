package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoBeneficiamentoRepository extends JpaRepository<EventoBeneficiamento, Long> {
    
    @Query("SELECT e FROM EventoBeneficiamento e " +
           "JOIN e.beneficiamento b " +
           "JOIN b.pontoColeta p " +
           "WHERE LOWER(p.bairro) = LOWER(:bairro)")
    List<EventoBeneficiamento> findByBairro(@Param("bairro") String bairro);
}
