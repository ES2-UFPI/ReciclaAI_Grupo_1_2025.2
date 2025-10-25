package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemEventoColetaRepository extends JpaRepository<ItemEventoColeta, Long> {
}
