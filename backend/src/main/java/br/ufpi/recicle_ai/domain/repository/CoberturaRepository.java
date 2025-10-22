package br.ufpi.recicle_ai.domain.repository;

import br.ufpi.recicle_ai.domain.model.Cobertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoberturaRepository extends JpaRepository<Cobertura, Long> {

}
