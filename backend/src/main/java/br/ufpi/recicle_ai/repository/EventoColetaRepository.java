package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoColetaRepository extends JpaRepository<EventoColeta, Long> {
    List<EventoColeta> findAllByProdutorId(Long produtorId);

    boolean existsByColetaIdAndProdutorId(Long coletaId, Long produtorId);

    List<EventoColeta> findAllByColetaColetorId(Long coletorId);
}
