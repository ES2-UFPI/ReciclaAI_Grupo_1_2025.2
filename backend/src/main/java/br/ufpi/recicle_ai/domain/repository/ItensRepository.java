package br.ufpi.recicle_ai.domain.repository;

import br.ufpi.recicle_ai.domain.model.Itens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensRepository extends JpaRepository<Itens, Long> {

}
